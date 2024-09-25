package com.tth.product.service.specification;

import com.tth.product.entity.Unit;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class UnitSpecification {

    public static Specification<Unit> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                String name = params.get("name");
                if (name != null && !name.isEmpty()) {
                    predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
                }

                String abbreviation = params.get("abbreviation");
                if (abbreviation != null && !abbreviation.isEmpty()) {
                    predicates.add(builder.like(root.get("abbreviation"), String.format("%%%s%%", abbreviation)));
                }
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
