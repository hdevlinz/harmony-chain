package com.tth.identity.service.specification;

import com.tth.identity.entity.Supplier;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SupplierSpecification {

    public static Specification<Supplier> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "name":
                            predicates.add(builder.like(root.get("name"), String.format("%%%s%%", value)));
                            break;
                        case "address":
                            predicates.add(builder.like(root.get("address"), String.format("%%%s%%", value)));
                            break;
                        case "phone":
                            predicates.add(builder.like(root.get("phone"), String.format("%%%s%%", value)));
                            break;
                        case "contactInfo":
                            predicates.add(builder.like(root.get("contactInfo"), String.format("%%%s%%", value)));
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
