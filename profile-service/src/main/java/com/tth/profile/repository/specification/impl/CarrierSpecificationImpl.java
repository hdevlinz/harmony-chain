package com.tth.profile.repository.specification.impl;

import com.tth.profile.entity.Carrier;
import com.tth.profile.repository.specification.CarrierSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
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
public class CarrierSpecificationImpl implements CarrierSpecification {

    private final Neo4jClient neo4jClient;

    @Override
    public Page<Carrier> filter(Map<String, String> params, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("MATCH (c:carrier) WHERE c.active = true");
        Map<String, Object> queryParams = new HashMap<>();

        params.forEach((key, value) -> {
            switch (key) {
                case "name" -> {
                    queryBuilder.append(" AND c.name CONTAINS $name");
                    queryParams.put("name", value);
                }
                case "contactInfo" -> {
                    queryBuilder.append(" AND c.contactInfo CONTAINS $contactInfo");
                    queryParams.put("contactInfo", value);
                }
                default -> log.warn("Unknown filter key: {}", key);
            }
        });

        queryBuilder.append(" RETURN c.id AS id," +
                " c.name AS name," +
                " c.contactInfo AS contactInfo" +
                " SKIP $skip LIMIT $limit"
        );
        queryParams.put("skip", pageable.getPageNumber() * pageable.getPageSize());
        queryParams.put("limit", pageable.getPageSize());

        List<Carrier> result = this.neo4jClient.query(queryBuilder.toString())
                .bindAll(queryParams)
                .fetchAs(Carrier.class)
                .mappedBy((TypeSystem type, Record record) -> {
                    Carrier carrier = new Carrier();
                    carrier.setId(!record.get("id").isNull() ? record.get("id").asString() : null);
                    carrier.setName(!record.get("name").isNull() ? record.get("name").asString() : null);
                    carrier.setContactInfo(!record.get("contactInfo").isNull() ? record.get("contactInfo").asString() : null);
                    return carrier;
                }).all().stream().toList();

        return new PageImpl<>(result, pageable, result.size());
    }

}
