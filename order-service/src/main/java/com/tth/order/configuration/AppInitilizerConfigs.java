package com.tth.order.configuration;

import com.tth.order.dto.request.OrderDetailsRequest;
import com.tth.order.dto.request.OrderRequest;
import com.tth.order.dto.response.inventory.InventoryDetailsResponse;
import com.tth.order.dto.response.product.ProductListResponse;
import com.tth.order.dto.response.user.User;
import com.tth.order.entity.Inventory;
import com.tth.order.entity.InventoryDetails;
import com.tth.order.entity.Warehouse;
import com.tth.order.enums.OrderStatus;
import com.tth.order.enums.OrderType;
import com.tth.order.mapper.InventoryMapper;
import com.tth.order.repository.InventoryDetailsRepository;
import com.tth.order.repository.InventoryRepository;
import com.tth.order.repository.WarehouseRepository;
import com.tth.order.repository.httpclient.IdentityClient;
import com.tth.order.repository.httpclient.ProductClient;
import com.tth.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppInitilizerConfigs {

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    public ApplicationRunner applicationRunner(
            InventoryDetailsRepository inventoryDetailsRepository,
            WarehouseRepository warehouseRepository,
            InventoryRepository inventoryRepository,
            OrderService orderService,
            ProductClient productClient,
            IdentityClient identityClient,
            InventoryMapper inventoryMapper
    ) {
        return args -> {
            log.info("Initializing application.....");

            if (warehouseRepository.count() == 0) {
                log.info("Creating warehouses.....");
                this.createWarehouse(warehouseRepository);

                log.info("Creating inventories.....");
                this.createInventory(warehouseRepository, inventoryRepository, productClient);

                log.info("Creating orders.....");
                this.createOrder(inventoryDetailsRepository, orderService, identityClient, inventoryMapper);
            }

            log.info("Application initialization completed.....");
        };
    }

    private void createWarehouse(WarehouseRepository warehouseRepository) {
        warehouseRepository.saveAll(List.of(
                Warehouse.builder().name("Warehouse 1").location("TPHCM").capacity(50000000.0F).cost(new BigDecimal(100000)).build(),
                Warehouse.builder().name("Warehouse 2").location("Hà Nội").capacity(100000000.0F).cost(new BigDecimal(200000)).build(),
                Warehouse.builder().name("Warehouse 3").location("Đà Nẵng").capacity(150000000.0F).cost(new BigDecimal(300000)).build(),
                Warehouse.builder().name("Warehouse 4").location("Cần Thơ").capacity(200000000.0F).cost(new BigDecimal(400000)).build(),
                Warehouse.builder().name("Warehouse 5").location("Hải Phòng").capacity(250000000.0F).cost(new BigDecimal(500000)).build()
        ));
    }

    private void createInventory(WarehouseRepository warehouseRepository, InventoryRepository inventoryRepository, ProductClient productClient) {
        List<ProductListResponse> products = productClient.listProducts(null, 1, 10).getData();
        List<Warehouse> warehouses = warehouseRepository.findAll();
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

            Set<InventoryDetails> inventoryDetailsSet = randomProducts.parallelStream()
                    .map(product -> InventoryDetails.builder()
                            .quantity(5000 + (random.nextFloat() * (10000 - 100)))
                            .productId(product.getId())
                            .inventory(inventory)
                            .build())
                    .collect(Collectors.toSet());

            inventory.setInventoryDetails(inventoryDetailsSet);
            inventoryRepository.save(inventory);

            count.getAndIncrement();
        }));
    }

    private void createOrder(
            InventoryDetailsRepository inventoryDetailsRepository,
            OrderService orderService,
            IdentityClient identityClient,
            InventoryMapper inventoryMapper

    ) {
        List<InventoryDetails> inventoryDetails = inventoryDetailsRepository.findAll();
        List<InventoryDetailsResponse> inventoryDetailsResponses = new ArrayList<>(inventoryDetails
                .stream().map(inventoryMapper::toInventoryDetailsResponse).toList());
        List<User> users = identityClient.getAllUser().getResult();
        Random random = new Random();

        users.forEach(user -> IntStream.range(0, 10).forEach(index -> {
            Collections.shuffle(inventoryDetailsResponses, random);
            List<ProductListResponse> randomProducts = inventoryDetailsResponses.stream()
                    .map(InventoryDetailsResponse::getProduct).limit(3).toList();

            Set<OrderDetailsRequest> orderDetails = randomProducts.stream()
                    .map(product -> OrderDetailsRequest.builder()
                            .productId(product.getId())
                            .quantity(3F)
                            .unitPrice(product.getPrice())
                            .build())
                    .collect(Collectors.toSet());

            OrderRequest orderRequest = OrderRequest.builder()
                    .type(OrderType.values()[random.nextInt(OrderType.values().length)])
                    .status(OrderStatus.DELIVERED)
                    .paid(true)
                    .inventoryId(inventoryDetails.getFirst().getInventory().getId())
                    .orderDetails(orderDetails)
                    .createdAt(this.getRandomDateTimeInYear())
                    .build();

            if (orderRequest.getType() == OrderType.OUTBOUND) {
                orderService.checkout(orderRequest, user);
            } else if (orderRequest.getType() == OrderType.INBOUND) {
                orderService.checkin(orderRequest, user);
            }
        }));
    }

    private LocalDateTime getRandomDateTimeInYear() {
        // Lấy tháng hiện tại
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();

        // Random tháng từ 1 đến tháng hiện tại
        int randomMonth = ThreadLocalRandom.current().nextInt(1, currentMonth + 1);

        // Tạo ngày bắt đầu và ngày kết thúc cho tháng random
        LocalDate start = LocalDate.of(now.getYear(), randomMonth, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // Random ngày trong khoảng từ start đến end
        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay() + 1);

        // Chuyển đổi từ epoch day sang LocalDate và sau đó sang Date
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
    }

}
