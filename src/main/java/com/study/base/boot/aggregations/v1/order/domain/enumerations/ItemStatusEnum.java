package com.study.base.boot.aggregations.v1.order.domain.enumerations;

import lombok.Getter;

@Getter
public enum ItemStatusEnum {
    SELL("selling"),
    STOP("discontinued");

    private String status;

    ItemStatusEnum(String status){
        this.status = status;
    }
}