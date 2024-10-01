package com.tth.inventory.service.specification;

import com.tth.inventory.entity.InventoryItem;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class InventoryItemSpecification {

    public static Specification<InventoryItem> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                switch (key) {
                    case "inventory" -> predicates.add(builder.equal(root.get("inventory").get("id"), value));
                    case "product" -> predicates.add(builder.equal(root.get("productId"), value));
                    default -> log.warn("Unknown filter key: {}", key);
                }
            });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
