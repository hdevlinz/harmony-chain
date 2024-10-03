package com.tth.inventory.service.impl;

import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.inventory.entity.Inventory;
import com.tth.inventory.entity.InventoryItem;
import com.tth.inventory.entity.Warehouse;
import com.tth.inventory.repository.InventoryRepository;
import com.tth.inventory.repository.WarehouseRepository;
import com.tth.inventory.repository.httpclient.ProductClient;
import com.tth.inventory.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SampleDataServiceImpl implements SampleDataService {

    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    @Override
    public void createSampleData() {
        if (this.warehouseRepository.count() == 0) {
            log.info("Creating warehouses.....");
            this.createWarehouse();
        }

        if (this.inventoryRepository.count() == 0) {
            log.info("Creating inventories.....");
            this.createInventory();
        }
    }

    private void createWarehouse() {
        this.warehouseRepository.saveAll(List.of(
                Warehouse.builder().name("Warehouse 1").location("TPHCM").capacity(50000000.0F).cost(new BigDecimal(100000)).build(),
                Warehouse.builder().name("Warehouse 2").location("Hà Nội").capacity(100000000.0F).cost(new BigDecimal(200000)).build(),
                Warehouse.builder().name("Warehouse 3").location("Đà Nẵng").capacity(150000000.0F).cost(new BigDecimal(300000)).build(),
                Warehouse.builder().name("Warehouse 4").location("Cần Thơ").capacity(200000000.0F).cost(new BigDecimal(400000)).build(),
                Warehouse.builder().name("Warehouse 5").location("Hải Phòng").capacity(250000000.0F).cost(new BigDecimal(500000)).build()
        ));
    }

    private void createInventory() {
        List<ProductListResponse> products = this.productClient.listProducts(null, 1, 10).getData();
        List<Warehouse> warehouses = this.warehouseRepository.findAll();
        AtomicInteger count = new AtomicInteger(1);
        Random random = new Random();

        warehouses.forEach(warehouse -> IntStream.range(0, 10).forEach(index -> {
            Inventory inventory = Inventory.builder()
                    .name("Inventory " + count)
                    .warehouse(warehouse)
                    .build();

            Collections.shuffle(products, new Random());
            int numberOfProductsToReturn = 50 + new Random().nextInt(100 - 50 + 1);
            List<ProductListResponse> randomProducts = products.parallelStream().limit(numberOfProductsToReturn).toList();

            Set<InventoryItem> inventoryItemSet = randomProducts.parallelStream()
                    .map(product -> InventoryItem.builder()
                            .quantity(5000 + (random.nextFloat() * (10000 - 100)))
                            .productId(product.getId())
                            .inventory(inventory)
                            .build())
                    .collect(Collectors.toSet());

            inventory.setInventoryItems(inventoryItemSet);
            this.inventoryRepository.save(inventory);

            count.getAndIncrement();
        }));
    }

}
