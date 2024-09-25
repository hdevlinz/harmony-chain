package com.tth.inventory.service.specification;

import com.tth.inventory.entity.InventoryDetails;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class WarehouseSpecification {

    public static Specification<InventoryDetails> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                Arrays.asList("inventory", "product").forEach(key -> {
                    if (params.containsKey(key) && !params.get(key).isEmpty()) {
                        switch (key) {
                            case "inventory":
                                predicates.add(builder.equal(root.get("inventory").get("id"), params.get("inventory")));
                                break;
                            case "product":
                                predicates.add(builder.equal(root.get("product").get("id"), params.get("product")));
                                break;
                        }
                    }
                });
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
