package com.tth.order.repository;

import com.tth.order.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String>, JpaSpecificationExecutor<Inventory> {

    Optional<Inventory> findByName(String inventoryName);

    Optional<Inventory> findByWarehouseId(String warehouseId);

}
