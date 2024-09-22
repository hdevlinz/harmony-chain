package com.tth.identity.service.repository;

import com.tth.identity.service.entity.Supplier;
import com.tth.identity.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, JpaSpecificationExecutor<Supplier> {

    Optional<Supplier> findByUser(User user);
}
