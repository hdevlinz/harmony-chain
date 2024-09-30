package com.tth.profile.repository;

import com.tth.profile.entity.Customer;
import com.tth.profile.repository.specification.CustomerSpecification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer, String>, CustomerSpecification {

    boolean existsByUserId(String user);

    Optional<Customer> findByUserId(String user);

}
