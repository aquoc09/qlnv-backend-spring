package com.kenji.qlnv_backend.exception;

import com.kenji.qlnv_backend.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authorization.AuthorizationDeniedException;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ApiResponse<?> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return apiResponse;
    }

    @ExceptionHandler(value = Exception.class)
    ApiResponse<?> handlingException(Exception exception) {
        log.error("Exception: ", exception);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return apiResponse;
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        log.error("Exception: ", exception);
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(exception.getErrorCode().getCode());
        apiResponse.setMessage(exception.getErrorCode().getMessage());

        return ResponseEntity
                .status(exception.getErrorCode().getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<?> handlingAuthorizationDeniedException(AuthorizationDeniedException exception) {
        log.error("Exception: ", exception);
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ApiResponse<?> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        var enumKey = Objects.requireNonNull(exception.getFieldError().getDefaultMessage());

        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult()
                    .getAllErrors().get(0).unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());

        return apiResponse;
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
