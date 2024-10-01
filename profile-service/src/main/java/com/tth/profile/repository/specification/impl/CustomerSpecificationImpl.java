package com.tth.profile.repository.specification.impl;

import com.tth.profile.entity.Customer;
import com.tth.profile.repository.specification.CustomerSpecification;
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
public class CustomerSpecificationImpl implements CustomerSpecification {

    private final Neo4jClient neo4jClient;

    @Override
    public Page<Customer> filter(Map<String, String> params, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("MATCH (c:customer) WHERE c.active = true");
        Map<String, Object> queryParams = new HashMap<>();

        params.forEach((key, value) -> {
            switch (key) {
                case "name" -> {
                    queryBuilder.append(" AND (c.firstName CONTAINS $name OR c.middleName CONTAINS $name OR c.lastName CONTAINS $name)");
                    queryParams.put("name", value);
                }
                case "address" -> {
                    queryBuilder.append(" AND c.address CONTAINS $address");
                    queryParams.put("address", value);
                }
                case "phone" -> {
                    queryBuilder.append(" AND c.phone CONTAINS $phone");
                    queryParams.put("phone", value);
                }
                default -> log.warn("Unknown filter key: {}", key);
            }
        });

        queryBuilder.append(" RETURN c.id AS id," +
                " c.firstName AS firstName," +
                " c.middleName AS middleName," +
                " c.lastName AS lastName," +
                " c.address AS address," +
                " c.phone AS phone," +
                " c.gender AS gender," +
                " c.dateOfBirth AS dateOfBirth" +
                " SKIP $skip LIMIT $limit"
        );
        queryParams.put("skip", pageable.getPageNumber() * pageable.getPageSize());
        queryParams.put("limit", pageable.getPageSize());

        List<Customer> result = neo4jClient.query(queryBuilder.toString())
                .bindAll(queryParams)
                .fetchAs(Customer.class)
                .mappedBy((type, record) -> {
                    Customer customer = new Customer();
                    customer.setId(!record.get("id").isNull() ? record.get("id").asString() : null);
                    customer.setFirstName(!record.get("firstName").isNull() ? record.get("firstName").asString() : null);
                    customer.setMiddleName(!record.get("middleName").isNull() ? record.get("middleName").asString() : null);
                    customer.setLastName(!record.get("lastName").isNull() ? record.get("lastName").asString() : null);
                    customer.setAddress(!record.get("address").isNull() ? record.get("address").asString() : null);
                    customer.setPhone(!record.get("phone").isNull() ? record.get("phone").asString() : null);
                    customer.setGender(!record.get("gender").isNull() ? record.get("gender").asBoolean() : null);
                    customer.setDateOfBirth(!record.get("dateOfBirth").isNull() ? record.get("dateOfBirth").asLocalDate() : null);
                    return customer;
                }).all().stream().toList();

        return new PageImpl<>(result, pageable, result.size());
    }

}
