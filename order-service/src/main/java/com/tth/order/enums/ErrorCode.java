package com.tth.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    WAREHOUSE_EXISTS(1404, "Nhà kho đã tồn tại.", HttpStatus.CONFLICT),
    ORDER_NOT_FOUND(1404, "Đơn hàng không tồn tại.", HttpStatus.NOT_FOUND),
    INVOICE_NOT_FOUND(1404, "Hóa đơn không tồn tại.", HttpStatus.NOT_FOUND),
    WAREHOUSE_NOT_FOUND(1404, "Nhà kho không tồn tại.", HttpStatus.NOT_FOUND),
    INVENTORY_NOT_FOUND(1404, "Tồn kho không tồn tại.", HttpStatus.NOT_FOUND),
    INVENTORY_DETAILS_NOT_FOUND(1404, "Chi tiết tồn kho không tồn tại.", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_EXISTS_IN_CART(1404, "Sản phẩm không tồn tại trong giỏ hàng.", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(2401, "Xác thực không thành công.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2403, "Bạn không có quyền truy cập tài nguyên này.", HttpStatus.FORBIDDEN),

    INVALID_REQUEST(9001, "Yêu cầu không hợp lệ.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_ERROR(9999, "Lỗi không xác định.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final String message;

    private final HttpStatusCode httpStatusCode;

}
