package com.tth.identity.repository.specification;

import com.tth.identity.entity.User;
import com.tth.identity.enums.UserRole;
import com.tth.identity.util.Utils;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class UserSpecification {

    public static Specification<User> filterUsers(Map<String, String> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("active"), true));

            if (params != null && !params.isEmpty()) {
                Boolean isConfirm = Utils.parseBoolean(params.get("isConfirm"));
                if (isConfirm != null) {
                    predicates.add(builder.equal(root.get("isConfirm"), isConfirm));
                }

                String roleStr = params.get("role");
                if (StringUtils.hasText(roleStr)) {
                    try {
                        UserRole userRole = UserRole.valueOf(roleStr.toUpperCase(Locale.getDefault()));
                        predicates.add(builder.equal(root.get("role"), userRole));
                    } catch (IllegalArgumentException e) {
                        log.error("An error parse Role Enum", e);
                    }
                }
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

}
