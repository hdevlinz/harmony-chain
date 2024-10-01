package com.tth.profile.repository.specification.impl;

import com.tth.profile.entity.Supplier;
import com.tth.profile.repository.specification.SupplierSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SupplierSpecificationImpl implements SupplierSpecification {

    private final Neo4jClient neo4jClient;

    @Override
    public Page<Supplier> filter(Map<String, String> params, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("MATCH (s:supplier) WHERE s.active = true");
        Map<String, Object> queryParams = new HashMap<>();

        params.forEach((key, value) -> {
            switch (key) {
                case "name" -> {
                    queryBuilder.append(" AND s.name CONTAINS $name");
                    queryParams.put("name", value);
                }
                case "address" -> {
                    queryBuilder.append(" AND s.address CONTAINS $address");
                    queryParams.put("address", value);
                }
                case "phone" -> {
                    queryBuilder.append(" AND s.phone CONTAINS $phone");
                    queryParams.put("phone", value);
                }
                case "contactInfo" -> {
                    queryBuilder.append(" AND s.contactInfo CONTAINS $contactInfo");
                    queryParams.put("contactInfo", value);
                }
                default -> log.warn("Unknown filter key: {}", key);
            }
        });

        queryBuilder.append(" RETURN s.id AS id," +
                " s.name AS name," +
                " s.address AS address," +
                " s.phone AS phone," +
                " s.contactInfo AS contactInfo" +
                " SKIP $skip LIMIT $limit"
        );
        queryParams.put("skip", pageable.getPageNumber() * pageable.getPageSize());
        queryParams.put("limit", pageable.getPageSize());

        List<Supplier> result = neo4jClient.query(queryBuilder.toString())
                .bindAll(queryParams)
                .fetchAs(Supplier.class)
                .mappedBy((type, record) -> {
                    Supplier supplier = new Supplier();
                    supplier.setId(!record.get("id").isNull() ? record.get("id").asString() : null);
                    supplier.setName(!record.get("name").isNull() ? record.get("name").asString() : null);
                    supplier.setAddress(!record.get("address").isNull() ? record.get("address").asString() : null);
                    supplier.setPhone(!record.get("phone").isNull() ? record.get("phone").asString() : null);
                    supplier.setContactInfo(!record.get("contactInfo").isNull() ? record.get("contactInfo").asString() : null);
                    return supplier;
                }).all().stream().toList();

        return new PageImpl<>(result, pageable, result.size());
    }

}
