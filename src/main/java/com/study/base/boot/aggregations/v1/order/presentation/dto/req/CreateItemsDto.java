package com.study.base.boot.aggregations.v1.order.presentation.dto.req;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemsDto {
    private List<CreateItemDto> createItems;

    public List<CreateItem> toCreateItem(){
        return this.createItems.stream()
                .map(CreateItemDto::toCreate)
                .collect(Collectors.toList());
    }
}