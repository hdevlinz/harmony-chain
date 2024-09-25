package com.tth.product.service.specification;

import com.tth.product.entity.Category;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class CategorySpecification {

    public static Specification<Category> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                String name = params.get("name");
                if (name != null && !name.isEmpty()) {
                    predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
                }
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
