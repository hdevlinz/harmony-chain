package com.tth.order.service.specification;

import com.tth.order.entity.OrderDetails;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderDetailsSpecification {

    public static Specification<OrderDetails> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "product":
                            predicates.add(builder.equal(root.get("productId"), value));
                            break;
                        case "fromPrice":
                            predicates.add(builder.greaterThanOrEqualTo(root.get("unitPrice"), new BigDecimal(value)));
                            break;
                        case "toPrice":
                            predicates.add(builder.lessThanOrEqualTo(root.get("unitPrice"), new BigDecimal(value)));
                            break;
                        case "order":
                            predicates.add(builder.equal(root.get("order").get("id"), value));
                            break;
                        default:
                            log.warn("Unknown filter key: {}", key);
                    }
                }
            });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
