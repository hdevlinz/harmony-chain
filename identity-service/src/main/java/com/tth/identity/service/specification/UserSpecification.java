package com.tth.identity.service.specification;

import com.tth.commonlibrary.enums.UserRole;
import com.tth.commonlibrary.utils.ConverterUtils;
import com.tth.identity.entity.User;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class UserSpecification {

    public static Specification<User> filter(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            params.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "isConfirm":
                            predicates.add(builder.equal(root.get("isConfirm"), ConverterUtils.parseBoolean(value)));
                            break;
                        case "role":
                            try {
                                UserRole userRole = UserRole.valueOf(value.toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("role"), userRole));
                            } catch (IllegalArgumentException e) {
                                log.error("An error parse Role Enum", e);
                            }
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
