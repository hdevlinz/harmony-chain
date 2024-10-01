package com.tth.inventory.repository;

import com.tth.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, String>, JpaSpecificationExecutor<InventoryItem> {

    @Query("SELECT SUM(i.quantity) FROM InventoryItem i WHERE i.inventory.warehouse.id = :warehouseId")
    Float getTotalQuantityByWarehouseId(@Param("warehouseId") String warehouseId);

}
