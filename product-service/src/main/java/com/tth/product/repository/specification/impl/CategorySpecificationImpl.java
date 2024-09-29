package com.tth.product.repository.specification.impl;

import com.tth.product.entity.Category;
import com.tth.product.repository.specification.CategorySpecification;
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
public class CategorySpecificationImpl implements CategorySpecification {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Category> filter(Map<String, String> params, Pageable pageable) {
        Query query = new Query();

        params.forEach((key, value) -> {
            if (value != null && !value.isEmpty()) {
                switch (key) {
                    case "name":
                        query.addCriteria(Criteria.where("name").regex(value, "i"));
                        break;
                    case "description":
                        query.addCriteria(Criteria.where("description").regex(value, "i"));
                        break;
                    default:
                        log.warn("Unknown filter key: {}", key);
                }
            }
        });

        long total = this.mongoTemplate.count(query, Category.class);
        query.with(pageable);

        List<Category> categories = this.mongoTemplate.find(query, Category.class);

        return new PageImpl<>(categories, pageable, total);
    }

}
