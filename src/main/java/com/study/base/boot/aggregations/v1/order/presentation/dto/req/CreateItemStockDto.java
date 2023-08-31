package com.study.base.boot.aggregations.v1.order.presentation.dto.req;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItemStock;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Builder
@Getter
public class CreateItemStockDto {
    @PositiveOrZero
    private int stockQty;

    public CreateItemStock toCreate(){
        return CreateItemStock.builder()
                .stockQty(this.stockQty)
                .build();
    }
}