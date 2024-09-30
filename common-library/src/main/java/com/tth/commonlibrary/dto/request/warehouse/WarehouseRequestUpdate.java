package com.tth.commonlibrary.dto.request.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequestUpdate {

    private String name;

    private String location;

    private Float capacity;

    private BigDecimal cost;

}
