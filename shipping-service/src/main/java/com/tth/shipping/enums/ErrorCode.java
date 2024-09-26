package com.tth.shipping.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHENTICATED(2401, "Xác thực không thành công.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2403, "Bạn không có quyền truy cập tài nguyên này.", HttpStatus.FORBIDDEN),

    INVALID_REQUEST(9001, "Yêu cầu không hợp lệ.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_ERROR(9999, "Lỗi không xác định.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final String message;

    private final HttpStatusCode httpStatusCode;

}
