package com.study.base.boot.aggregations.v1.order.presentation.dto.res;

import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderItemStatusEnum;
import com.study.base.boot.config.mapstruct.base.BaseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@SuperBuilder// 부모가지 빌더로 바꿔주는것 대신 부모도 superBuilder이 달려있어야한다.
@Getter
public class OrderItemDto extends BaseDto {
    private Long id;
    private long itemId;
    private String itemName;

    private OrderItemStatusEnum status;
    private int price;
    private int qty;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
