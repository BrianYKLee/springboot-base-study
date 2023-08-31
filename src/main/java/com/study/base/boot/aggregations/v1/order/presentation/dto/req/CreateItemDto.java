package com.study.base.boot.aggregations.v1.order.presentation.dto.req;


import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItem;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.ItemStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CreateItemDto {
    @NotNull
    String itemName;

    @Enumerated(EnumType.STRING)
    ItemStatusEnum status;
    @PositiveOrZero
    int price;
    @NotNull
    @Size(min = 1)
    @Valid     // valid 는 내 하위의 객체만 체크한다.
    private List<CreateItemStockDto> items;


    public CreateItem toCreate() {
        return CreateItem.builder()
                .itemName(this.itemName)
                .price(this.price)
                .items(this.items.stream()
                        .map(CreateItemStockDto::toCreate)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
