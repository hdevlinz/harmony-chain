package com.tth.inventory.repository;

import com.tth.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String>, JpaSpecificationExecutor<Inventory> {

    Optional<Inventory> findByName(String inventoryName);

    Optional<Inventory> findByWarehouseId(String warehouseId);

}
