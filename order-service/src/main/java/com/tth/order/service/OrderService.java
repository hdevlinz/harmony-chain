package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.order.OrderRequest;
import com.tth.commonlibrary.dto.response.order.OrderResponse;
import com.tth.commonlibrary.dto.response.user.UserResponse;
import com.tth.order.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse findByOrderNumber(String orderNumber);

    void checkout(OrderRequest orderRequest, UserResponse user);

    void checkin(OrderRequest orderRequest, UserResponse user);

    void cancelOrder(String orderNumber);

    void updateOrderStatus(String orderNumber, String status);

    List<OrderResponse> findRecentlyOrders();

    PageResponse<Order> findAllByParams(Map<String, String> params, int page, int size);

    PageResponse<Order> findByShipmentId(String deliveryScheduleId, int page, int size);

    PageResponse<OrderResponse> findAllOrderOfAuthenticated(Map<String, String> params, int page, int size);

    PageResponse<Order> findAllBySupplierId(String supplierId, Map<String, String> params, int page, int size);

}
