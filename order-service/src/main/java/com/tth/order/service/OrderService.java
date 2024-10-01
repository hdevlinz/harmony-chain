package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.order.OrderRequest;
import com.tth.commonlibrary.dto.response.identity.user.UserResponse;
import com.tth.commonlibrary.dto.response.order.OrderResponse;

import java.util.Map;

public interface OrderService {

    OrderResponse findByOrderNumber(String orderNumber);

    void checkout(OrderRequest orderRequest, UserResponse user);

    void checkin(OrderRequest orderRequest, UserResponse user);

    void cancelOrder(String orderNumber);

    void updateOrderStatus(String orderNumber, String status);

    PageResponse<OrderResponse> findAllOfAuthenticated(Map<String, String> params, int page, int size);

}
