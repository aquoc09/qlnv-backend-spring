package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.AuthenticationRequest;
import com.kenji.qlnv_backend.dto.request.TokenRequest;
import com.kenji.qlnv_backend.dto.response.AuthenticationResponse;
import com.kenji.qlnv_backend.dto.response.IntrospectResponse;
import com.kenji.qlnv_backend.entity.Token;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.repository.TokenRepository;
import com.kenji.qlnv_backend.repository.UserRepository;
import com.kenji.qlnv_backend.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImp implements AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(TokenRequest request){
        var token = request.getToken();
        if(token==null||token.isBlank()){
            log.info("Token blank");
            return IntrospectResponse.builder()
                    .introspected(false)
                    .message("Token is blank")
                    .build();
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSHeader header = signedJWT.getHeader();
            if(!JWSAlgorithm.HS512.equals(header.getAlgorithm())){
                log.info("Token wrong alg");
                return IntrospectResponse.builder()
                        .introspected(false)
                        .message("Wrong algorithm")
                        .build();
            }

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            boolean verify = signedJWT.verify(verifier);
            if(!verify){
                log.info("Token verify fail");
                return IntrospectResponse.builder()
                        .introspected(false)
                        .message("Verify failed")
                        .build();
            }

            Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean validTime = expiredTime.after(new Date());

            return IntrospectResponse.builder()
                    .introspected(verify&&validTime)
                    .message("Introspect successful")
                    .build();
        } catch (ParseException | JOSEException e) {
            log.error("Introspect failed");
            return IntrospectResponse.builder()
                    .introspected(false)
                    .message("Error introspect")
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ParseException, JOSEException {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        Token tokenEntity = getEntityTokenFromStringToken(token);
        tokenRepository.save(tokenEntity);


        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public boolean logout(TokenRequest request) throws ParseException, JOSEException {
        try {
            Token requestToken = getEntityTokenFromStringToken(request.getToken());
            Token currentToken = tokenRepository.findByJwtId(requestToken.getJwtId()).get();
            tokenRepository.delete(currentToken);
            return true;
        } catch (AppException e) {
            log.info("Token already expired");
            return false;
        }
    }

    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .issuer("com.kenji")
                .issueTime(new Date())
                .claim("refreshToken", UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create jwt token");
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException, AppException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.MINUTES)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!verified || expiryTime.before(new Date())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private Token getEntityTokenFromStringToken(String token) throws ParseException, JOSEException {
        var signedJWT = verifyToken(token, false);
        String refreshToken = signedJWT.getJWTClaimsSet().getClaim("refreshToken").toString();
        String username = signedJWT.getJWTClaimsSet().getSubject().toString();
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        LocalDateTime expirationDateTime = expiredTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return Token.builder()
                .jwtId(jwtId)
                .expirationTime(expirationDateTime)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(TokenRequest request) throws ParseException, JOSEException {

        var signedJWT = verifyToken(request.getToken(), true);
        var issueTime = signedJWT.getJWTClaimsSet().getIssueTime().toInstant();
        var refreshableTime = new Date(issueTime.plus(REFRESHABLE_DURATION, ChronoUnit.MINUTES).toEpochMilli());
        var jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(refreshableTime.before(new Date())){
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        String newToken = generateToken(user);
        Token entityToken = getEntityTokenFromStringToken(newToken);

        tokenRepository.deleteByJwtId(jwtId);
        tokenRepository.save(entityToken);

        return AuthenticationResponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner("");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));
        }
        return stringJoiner.toString();
    }
}
