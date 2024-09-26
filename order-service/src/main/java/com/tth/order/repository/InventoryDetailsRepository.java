package com.tth.order.repository;

import com.tth.order.entity.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryDetailsRepository extends JpaRepository<InventoryDetails, String>, JpaSpecificationExecutor<InventoryDetails> {

    List<InventoryDetails> findByProductId(String productId);

    List<InventoryDetails> findByInventoryId(String inventoryId);

    Optional<InventoryDetails> findByInventoryIdAndProductId(String inventoryId, String productId);

    @Query("SELECT SUM(i.quantity) FROM InventoryDetails i WHERE i.inventory.warehouse.id = :warehouseId")
    Float getTotalQuantityByWarehouseId(@Param("warehouseId") String warehouseId);

}
