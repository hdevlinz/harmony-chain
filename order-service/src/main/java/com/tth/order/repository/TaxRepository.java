package com.tth.order.repository;

import com.tth.order.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String>, JpaSpecificationExecutor<Tax> {

    Optional<Tax> findByRegion(String region);

}
