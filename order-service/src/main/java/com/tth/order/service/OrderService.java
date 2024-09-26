package com.tth.order.service;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.request.OrderRequest;
import com.tth.order.dto.response.OrderResponse;
import com.tth.order.dto.response.user.User;
import com.tth.order.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse findByOrderNumber(String orderNumber);

    void checkout(OrderRequest orderRequest, User user);

    void checkin(OrderRequest orderRequest, User user);

    void cancelOrder(String orderNumber);

    void updateOrderStatus(String orderNumber, String status);

    List<OrderResponse> findRecentlyOrders();

    PageResponse<Order> findAllByParams(Map<String, String> params, int page, int size);

    PageResponse<Order> findByDeliveryScheduleId(String deliveryScheduleId, int page, int size);

    PageResponse<OrderResponse> findAllOrderOfAuthenticated(Map<String, String> params, int page, int size);

    PageResponse<Order> findAllBySupplierId(String supplierId, Map<String, String> params, int page, int size);

}
