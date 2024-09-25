package com.tth.identity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOMER_NOT_FOUND(1404, "Khách hàng không tồn tại.", HttpStatus.NOT_FOUND),
    SHIPPED_NOT_FOUND(1404, "Đối tác vận chuyển không tồn tại.", HttpStatus.NOT_FOUND),
    SUPPLIER_NOT_FOUND(1404, "Nhà cung cấp không tồn tại.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1404, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),

    USER_EXISTED(1409, "Tài khoản đã tồn tại.", HttpStatus.CONFLICT),
    USERNAME_EXISTED(1409, "Tên tài khoản đã tồn tại.", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1409, "Địa chỉ email đã tồn tại.", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT(1409, "Mật khẩu không chính xác.", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(2401, "Xác thực không thành công.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2403, "Bạn không có quyền truy cập tài nguyên này.", HttpStatus.FORBIDDEN),

    INVALID_REQUEST(9001, "Yêu cầu không hợp lệ.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_ERROR(9999, "Lỗi không xác định.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final String message;

    private final HttpStatusCode httpStatusCode;

}
