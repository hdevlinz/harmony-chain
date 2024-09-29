package com.tth.inventory.controller.api;

import com.tth.order.dto.APIResponse;
import com.tth.order.dto.request.OrderRequest;
import com.tth.order.dto.response.OrderResponse;
import com.tth.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping(path = "/orders", produces = "application/json; charset=UTF-8")
public class APIOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrderOfAuthenticated(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.orderService.findAllOrderOfAuthenticated(params, page, size));
    }

    @GetMapping(path = "/{orderNumber}")
    public ResponseEntity<?> findOrderByOrderNumber(@PathVariable String orderNumber) {
        OrderResponse order = this.orderService.findByOrderNumber(orderNumber);

        return ResponseEntity.ok(APIResponse.<OrderResponse>builder().result(order).build());
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> checkout(@RequestBody @Valid OrderRequest orderRequest) {
        this.orderService.checkout(orderRequest, null);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/checkin")
    public ResponseEntity<?> checkin(@RequestBody @Valid OrderRequest orderRequest) {
        this.orderService.checkin(orderRequest, null);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(path = "/{orderNumber}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderNumber) {
        this.orderService.cancelOrder(orderNumber);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{orderNumber}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderNumber, @RequestBody Map<String, String> request) {
        this.orderService.updateOrderStatus(orderNumber, request.get("status"));

        return ResponseEntity.ok().build();
    }

}
