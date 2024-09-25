package com.tth.product.service.specification;

import com.tth.product.entity.Product;
import com.tth.product.entity.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductSpecification {

    public static Specification<Product> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                Arrays.asList("supplier", "name", "fromPrice", "toPrice", "category", "unit", "tags").forEach(key -> {
                    if (params.containsKey(key) && !params.get(key).isEmpty()) {
                        switch (key) {
                            case "supplier":
                                Predicate p1 = builder.equal(root.get("supplierId"), Long.parseLong(params.get("supplier")));
                                predicates.add(p1);
                                break;
                            case "name":
                                Predicate p2 = builder.like(root.get("name"), String.format("%%%s%%", params.get("name")));
                                predicates.add(p2);
                                break;
                            case "fromPrice":
                                Predicate p3 = builder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(params.get("fromPrice")));
                                predicates.add(p3);
                                break;
                            case "toPrice":
                                Predicate p4 = builder.lessThanOrEqualTo(root.get("price"), new BigDecimal(params.get("toPrice")));
                                predicates.add(p4);
                                break;
                            case "category":
                                Predicate p5 = builder.equal(root.get("category").get("id"), params.get("category"));
                                predicates.add(p5);
                                break;
                            case "unit":
                                Predicate p6 = builder.equal(root.get("unit").get("id"), params.get("unit"));
                                predicates.add(p6);
                                break;
                            case "tags":
                                List<String> tagIdList = List.of(params.get("tags").split(","));

                                Join<Product, Tag> tagJoin = root.join("tags");
                                Predicate tagPredicate = tagJoin.get("id").in(tagIdList);
                                predicates.add(tagPredicate);
                                break;
                        }
                    }
                });
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
