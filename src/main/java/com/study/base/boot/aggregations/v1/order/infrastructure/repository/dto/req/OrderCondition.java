package com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.req;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderCondition {
//repository에 쓰는 조회 조건 용도 이중에 하나만 쓸때도 그냥 돌아가게 한다.
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int price;
    private Pageable pageable;
}
