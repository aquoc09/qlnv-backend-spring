package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.AuthenticationRequest;
import com.kenji.qlnv_backend.dto.request.IntrospectRequest;
import com.kenji.qlnv_backend.dto.response.AuthenticationResponse;
import com.kenji.qlnv_backend.dto.response.IntrospectResponse;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request){
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

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.kenji")
                .issueTime(new Date())
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

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner("");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));
        }
        return stringJoiner.toString();
    }
}
