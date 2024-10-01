package com.tth.order.service.specification;

import com.tth.commonlibrary.enums.OrderStatus;
import com.tth.commonlibrary.enums.OrderType;
import com.tth.order.entity.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class OrderSpecification {

    public static Specification<Order> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                params.forEach((key, value) -> {
                    switch (key) {
                        case "user" -> predicates.add(builder.equal(root.get("userId"), value));
                        case "shipment" -> predicates.add(builder.equal(root.get("shipmentId"), value));
                        case "invoice" -> predicates.add(builder.equal(root.get("invoice").get("id"), value));
                        case "type" -> {
                            try {
                                OrderType type = OrderType.valueOf(value.toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("type"), type));
                            } catch (IllegalArgumentException e) {
                                log.error("Error parsing OrderType Enum", e);
                            }
                        }
                        case "status" -> {
                            try {
                                OrderStatus status = OrderStatus.valueOf(value.toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("status"), status));
                            } catch (IllegalArgumentException e) {
                                log.error("Error parsing OrderStatus Enum", e);
                            }
                        }
                        default -> log.warn("Unknown filter key: {}", key);
                    }
                });
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
