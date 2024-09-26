package com.tth.product.service.specification;

import com.tth.product.entity.Product;
import com.tth.product.entity.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductSpecification {

    public static Specification<Product> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "supplier":
                            predicates.add(builder.equal(root.get("supplierId"), value));
                            break;
                        case "name":
                            predicates.add(builder.like(root.get("name"), String.format("%%%s%%", value)));
                            break;
                        case "fromPrice":
                            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(value)));
                            break;
                        case "toPrice":
                            predicates.add(builder.lessThanOrEqualTo(root.get("price"), new BigDecimal(value)));
                            break;
                        case "category":
                            predicates.add(builder.equal(root.get("category").get("id"), value));
                            break;
                        case "unit":
                            predicates.add(builder.equal(root.get("unit").get("id"), value));
                            break;
                        case "tags":
                            List<String> tagIdList = List.of(value.split(","));

                            Join<Product, Tag> tagJoin = root.join("tags");
                            Predicate tagPredicate = tagJoin.get("id").in(tagIdList);
                            predicates.add(tagPredicate);
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
