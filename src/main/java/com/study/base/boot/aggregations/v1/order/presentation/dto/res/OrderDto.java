package com.study.base.boot.aggregations.v1.order.presentation.dto.res;

import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.config.mapstruct.base.BaseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class OrderDto extends BaseDto {
/*
OrderDto 생성이유
Entity를 client에 응답값으로 내려주게 되면 jackson Serialize시 순환참조로 stackoverflow
jpa lazy loading
컨트롤러와 서비스 레이어 분리 * */

    private Long id;

    private String orderNumber;
    private String orderName;

    /*EnumType.STRING : 각 Enum 이름을 컬럼에 저장한다. ex) G, PG, PG13..
     * EnumType.ORDINAL : 각 Enum에 대응되는 순서를 칼럼에 저장한다. ex) 0, 1, 2..*/
    private OrderStatusEnum status;
    private int price;
    private int deliveryFee;
    private String address;
    private long userId;

    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;


}
