package com.tth.commonlibrary.exception;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String defaultMessage = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        APIResponse<?> apiResponse = APIResponse.builder()
                .code(ErrorCode.INVALID_REQUEST.getCode())
                .message(defaultMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
