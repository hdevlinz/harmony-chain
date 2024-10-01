package com.tth.product.repository.specification.impl;

import com.tth.product.entity.Product;
import com.tth.product.repository.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductSpecificationImpl implements ProductSpecification {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Product> filter(Map<String, String> params, Pageable pageable) {
        Query query = new Query();

        params.forEach((key, value) -> {
            switch (key) {
                case "supplier" -> query.addCriteria(Criteria.where("supplierId").is(value));
                case "name" -> query.addCriteria(Criteria.where("name").regex(value, "i"));
                case "fromPrice" -> query.addCriteria(Criteria.where("price").gte(new BigDecimal(value)));
                case "toPrice" -> query.addCriteria(Criteria.where("price").lte(new BigDecimal(value)));
                case "category" -> query.addCriteria(Criteria.where("category.id").is(value));
                case "units" -> {
                    List<String> unitIdList = List.of(value.split(","));

                    query.addCriteria(Criteria.where("units.id").in(unitIdList));
                }
                case "tags" -> {
                    List<String> tagIdList = List.of(value.split(","));

                    query.addCriteria(Criteria.where("tags.id").in(tagIdList));
                }
                default -> log.warn("Unknown filter key: {}", key);
            }
        });

        long total = this.mongoTemplate.count(query, Product.class);
        query.with(pageable);

        List<Product> products = this.mongoTemplate.find(query, Product.class);

        return new PageImpl<>(products, pageable, total);
    }

}
