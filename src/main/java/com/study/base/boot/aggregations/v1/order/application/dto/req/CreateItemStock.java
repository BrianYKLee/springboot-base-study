package com.study.base.boot.aggregations.v1.order.application.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
public class CreateItemStock {
    private int stockQty;
}
