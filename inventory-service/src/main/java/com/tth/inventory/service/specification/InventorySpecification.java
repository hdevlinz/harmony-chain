package com.tth.inventory.service.specification;

import com.tth.inventory.entity.Inventory;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class InventorySpecification {

    public static Specification<Inventory> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                switch (key) {
                    case "name" -> predicates.add(builder.like(root.get("name"), String.format("%%%s%%", value)));
                    case "warehouse" -> predicates.add(builder.equal(root.get("warehouse").get("id"), value));
                    default -> log.warn("Unknown filter key: {}", key);
                }
            });

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
