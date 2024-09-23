package com.tth.identity.services.repository;

import com.tth.identity.services.entity.Shipper;
import com.tth.identity.services.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, String>, JpaSpecificationExecutor<Shipper> {

    Optional<Shipper> findByUser(User user);

}
