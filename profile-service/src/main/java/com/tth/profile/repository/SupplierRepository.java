package com.tth.profile.repository;

import com.tth.profile.entity.Supplier;
import com.tth.profile.repository.specification.SupplierSpecification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends Neo4jRepository<Supplier, String>, SupplierSpecification {

    boolean existsByUserId(String userId);

    Optional<Supplier> findByUserId(String userId);

}
