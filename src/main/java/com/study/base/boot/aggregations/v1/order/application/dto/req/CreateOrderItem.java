package com.study.base.boot.aggregations.v1.order.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrderItem {

    private long itemId;
    private String itemName;
    private int price;
    private int qty;

    //private OrderItemStatusEnum status;
}
