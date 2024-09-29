package com.tth.inventory.repository;

import com.tth.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByOrderNumberAndUserId(String orderNumber, String userId);

    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC LIMIT 10")
    List<Order> findRecentOrders();

    Page<Order> findByDeliveryScheduleId(String deliveryScheduleId, Pageable pageable);

}
