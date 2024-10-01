package com.tth.product.repository;

import com.tth.product.entity.Category;
import com.tth.product.repository.specification.CategorySpecification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String>, CategorySpecification {
}
