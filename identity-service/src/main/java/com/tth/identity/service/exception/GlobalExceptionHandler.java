package com.tth.identity.service.exception;

import com.tth.identity.service.dto.response.APIResponse;
import com.tth.identity.service.enums.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_ERROR.getCode())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_ERROR.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<?> handleAppException(AppException exception) {
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(exception.getErrorCode().getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.UNAUTHORIZED.getCode())
                .message(ErrorCode.UNAUTHORIZED.getMessage())
                .build();

        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(ConstraintViolationException exception) {
        String defaultMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst()
                .orElse("Yêu cầu không hợp lệ.");
        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.INVALID_REQUEST.getCode())
                .message(defaultMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
