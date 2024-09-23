package com.tth.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHENTICATED(2401, "Xác thực không thành công.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2403, "Bạn không có quyền truy cập tài nguyên này.", HttpStatus.FORBIDDEN),

    CANNOT_SEND_EMAIL(8001, "Không thể gửi email.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_ERROR(9999, "Lỗi không xác định.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final String message;

    private final HttpStatusCode httpStatusCode;

}
