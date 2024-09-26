package com.tth.order.service.specification;

import com.tth.order.entity.InventoryDetails;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class InventoryDetailsSpecification {

    public static Specification<InventoryDetails> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "inventory":
                            predicates.add(builder.equal(root.get("inventory").get("id"), value));
                            break;
                        case "product":
                            predicates.add(builder.equal(root.get("product").get("id"), value));
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
