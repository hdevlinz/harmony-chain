package com.tth.identity.service.repository;

import com.tth.identity.service.entity.Customer;
import com.tth.identity.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByUser(User user);
}
