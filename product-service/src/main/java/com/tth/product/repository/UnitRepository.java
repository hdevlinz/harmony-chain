package com.tth.product.repository;

import com.tth.product.entity.Unit;
import com.tth.product.repository.specification.UnitSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends MongoRepository<Unit, String>, UnitSpecification {
}
