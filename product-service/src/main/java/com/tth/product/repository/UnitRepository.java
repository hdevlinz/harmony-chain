package com.tth.product.repository;

import com.tth.product.entity.Unit;
import com.tth.product.repository.specification.UnitSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends MongoRepository<Unit, String>, UnitSpecification {

    Optional<Unit> findByName(String unitName);

    Optional<Unit> findByAbbreviation(String abbreviation);

}
