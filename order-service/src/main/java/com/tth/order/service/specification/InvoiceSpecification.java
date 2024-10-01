package com.tth.order.service.specification;

import com.tth.commonlibrary.utils.ConverterUtils;
import com.tth.order.entity.Invoice;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class InvoiceSpecification {

    public static Specification<Invoice> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "user":
                            predicates.add(builder.equal(root.get("userId"), value));
                            break;
                        case "isPaid":
                            predicates.add(builder.equal(root.get("paid"), ConverterUtils.parseBoolean(value)));
                            break;
                        case "fromCreatedAt":
                            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), ConverterUtils.parseLocalDateTime(value)));
                            break;
                        case "toCreatedAt":
                            predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), ConverterUtils.parseLocalDateTime(value)));
                            break;
                        case "tax":
                            predicates.add(builder.equal(root.get("tax").get("id"), value));
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
