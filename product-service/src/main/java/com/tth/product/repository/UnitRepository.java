package com.tth.product.repository;

import com.tth.product.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String>, JpaSpecificationExecutor<Unit> {

    Optional<Unit> findByName(String unitName);

    Optional<Unit> findByAbbreviation(String abbreviation);

}
