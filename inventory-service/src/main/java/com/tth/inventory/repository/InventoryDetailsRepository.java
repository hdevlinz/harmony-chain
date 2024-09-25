package com.tth.inventory.repository;

import com.tth.inventory.entity.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryDetailsRepository extends JpaRepository<InventoryDetails, String>, JpaSpecificationExecutor<InventoryDetails> {

    Optional<InventoryDetails> findByProductId(String productId);

    Iterable<InventoryDetails> findByInventoryId(String inventoryId);

}
