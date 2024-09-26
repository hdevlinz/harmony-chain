package com.tth.order.service.specification;

import com.tth.order.entity.Order;
import com.tth.order.entity.OrderDetails;
import com.tth.order.enums.OrderStatus;
import com.tth.order.enums.OrderType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class OrderSpecification {

    public static Specification<Order> filterByParams(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            filterByParms(predicates, root, builder, params);

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<Order> filterBySupplierId(String supplierId, Map<String, String> params) {
        return (root, query, builder) -> {
            Join<Order, OrderDetails> orderDetailsJoin = root.join("orderDetails");
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));
            predicates.add(builder.equal(orderDetailsJoin.get("productId"), supplierId));

            filterByParms(predicates, root, builder, params);

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static void filterByParms(
            List<Predicate> predicates,
            Root<Order> root,
            CriteriaBuilder builder,
            Map<String, String> params
    ) {
        if (params == null || params.isEmpty()) {
            return;
        }

        params.forEach((key, value) -> {
            if (value != null && !value.isEmpty()) {
                switch (key) {
                    case "user":
                        predicates.add(builder.equal(root.get("userId"), value));
                        break;
                    case "type":
                        try {
                            OrderType type = OrderType.valueOf(value.toUpperCase(Locale.getDefault()));
                            predicates.add(builder.equal(root.get("type"), type));
                        } catch (IllegalArgumentException e) {
                            log.error("Error parsing OrderType Enum", e);
                        }
                        break;
                    case "status":
                        try {
                            OrderStatus status = OrderStatus.valueOf(value.toUpperCase(Locale.getDefault()));
                            predicates.add(builder.equal(root.get("status"), status));
                        } catch (IllegalArgumentException e) {
                            log.error("Error parsing OrderStatus Enum", e);
                        }
                        break;
                    case "invoice":
                        predicates.add(builder.equal(root.get("invoice").get("id"), value));
                        break;
                    default:
                        log.warn("Unknown filter key: {}", key);
                }
            }
        });
    }

}
