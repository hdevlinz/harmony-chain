package com.tth.identity.services.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CUSTOMER_NOT_FOUND(1002, "Khách hàng không tồn tại.", HttpStatus.NOT_FOUND),
    SHIPPED_NOT_FOUND(1003, "Đối tác vận chuyển không tồn tại.", HttpStatus.NOT_FOUND),
    SUPPLIER_NOT_FOUND(1004, "Nhà cung cấp không tồn tại.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1005, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),

    USER_EXISTED(1010, "Tài khoản đã tồn tại.", HttpStatus.CONFLICT),
    USERNAME_EXISTED(1006, "Tên tài khoản đã tồn tại.", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1007, "Địa chỉ email đã tồn tại.", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT(1009, "Mật khẩu không chính xác.", HttpStatus.BAD_REQUEST),
//    USER_NOT_CONFIRMED(1010, "Tài khoản chưa được xác nhận.", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(2001, "Xác thực không thành công.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2002, "Bạn không có quyền truy cập tài nguyên này.", HttpStatus.FORBIDDEN),

    INVALID_REQUEST(9001, "Yêu cầu không hợp lệ.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_ERROR(9999, "Lỗi không xác định.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final String message;

    private final HttpStatusCode httpStatusCode;

    ErrorCode(int errorCode, String message, HttpStatusCode httpStatusCode) {
        this.code = errorCode;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
