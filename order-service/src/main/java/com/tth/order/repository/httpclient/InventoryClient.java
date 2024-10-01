package com.tth.order.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "inventory-client", url = "${app.services.inventory.url}")
public interface InventoryClient {

    @GetMapping(path = "/inventories")
    PageResponse<InventoryResponse> listInventories(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                    @RequestParam(required = false, defaultValue = "1") int page,
                                                    @RequestParam(required = false, defaultValue = "5") int size);

    @GetMapping(path = "/inventories/{inventoryId}")
    APIResponse<InventoryResponse> getInventory(@PathVariable String inventoryId);

}
