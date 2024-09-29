package com.tth.product.repository.specification.impl;

import com.tth.product.entity.Unit;
import com.tth.product.repository.specification.UnitSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UnitSpecificationImpl implements UnitSpecification {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Unit> filter(Map<String, String> params, Pageable pageable) {
        Query query = new Query();

        params.forEach((key, value) -> {
            if (value != null && !value.isEmpty()) {
                switch (key) {
                    case "name":
                        query.addCriteria(Criteria.where("name").regex(value, "i"));
                        break;
                    case "abbreviation":
                        query.addCriteria(Criteria.where("abbreviation").regex(value, "i"));
                        break;
                    default:
                        log.warn("Unknown filter key: {}", key);
                }
            }
        });

        long total = this.mongoTemplate.count(query, Unit.class);
        query.with(pageable);

        List<Unit> units = this.mongoTemplate.find(query, Unit.class);

        return new PageImpl<>(units, pageable, total);
    }

}
