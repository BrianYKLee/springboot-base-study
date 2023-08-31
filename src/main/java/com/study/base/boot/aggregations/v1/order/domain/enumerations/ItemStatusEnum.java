package com.study.base.boot.aggregations.v1.order.domain.enumerations;

import lombok.Getter;

@Getter
public enum ItemStatusEnum {
    SELL("SELLING"),
    STOP("DISCONTINUED");

    private String status;

    ItemStatusEnum(String status){
        this.status = status;
    }
}
