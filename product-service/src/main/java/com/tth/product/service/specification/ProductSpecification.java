package com.tth.product.service.specification;

import com.tth.identity.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductSpecification {

    public static Specification<Customer> filterCustomers(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                String name = params.get("name");
                if (name != null && !name.isEmpty()) {
                    List<Predicate> namePredicates = new ArrayList<>();

                    Arrays.asList("lastName", "middleName", "firstName").forEach(key -> {
                        namePredicates.add(builder.like(root.get(key), String.format("%%%s%%", name)));
                    });

                    predicates.add(builder.or(namePredicates.toArray(Predicate[]::new)));
                }
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
