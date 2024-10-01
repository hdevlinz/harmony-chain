package com.tth.product.repository.specification.impl;

import com.tth.product.entity.Tag;
import com.tth.product.repository.specification.TagSpecification;
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
public class TagSpecificationImpl implements TagSpecification {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Tag> filter(Map<String, String> params, Pageable pageable) {
        Query query = new Query();

        params.forEach((key, value) -> {
            switch (key) {
                case "name" -> query.addCriteria(Criteria.where("name").regex(value, "i"));
                case "description" -> query.addCriteria(Criteria.where("description").regex(value, "i"));
                default -> log.warn("Unknown filter key: {}", key);
            }
        });

        long total = this.mongoTemplate.count(query, Tag.class);
        query.with(pageable);

        List<Tag> tags = this.mongoTemplate.find(query, Tag.class);

        return new PageImpl<>(tags, pageable, total);
    }

}
