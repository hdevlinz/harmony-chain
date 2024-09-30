package com.tth.profile.repository;

import com.tth.profile.entity.Carrier;
import com.tth.profile.repository.specification.CarrierSpecification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrierRepository extends Neo4jRepository<Carrier, String>, CarrierSpecification {

    boolean existsByUserId(String userId);

    Optional<Carrier> findByUserId(String userId);

}
