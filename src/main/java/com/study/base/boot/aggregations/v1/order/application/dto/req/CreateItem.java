package com.study.base.boot.aggregations.v1.order.application.dto.req;

import com.study.base.boot.aggregations.v1.order.domain.enumerations.ItemStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class CreateItem {
    String itemName;

    int price;

    private List<CreateItemStock> items;
}