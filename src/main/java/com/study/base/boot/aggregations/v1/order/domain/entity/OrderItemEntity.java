package com.study.base.boot.aggregations.v1.order.domain.entity;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrderItem;
import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderItemStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(catalog = "base", name ="order_item")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Getter
@EntityListeners(AuditingEntityListener.class)
public class OrderItemEntity {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long itemId;
    private String itemName;

    @Enumerated(EnumType.STRING)
    private OrderItemStatusEnum status;
    private int price;
    private int qty;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", insertable = true, updatable = true)
    private OrderAggregate order;
    //여러개의 아이템에 대한 하나의 주문이니까

    public OrderItemEntity putOrder(OrderAggregate order){
        this.order = order;

        return this;
    }
    public OrderItemEntity patch(CreateOrderItem createOrderItem){
        this.itemId = createOrderItem.getItemId();
        this.itemName= StringUtils.defaultIfEmpty(createOrderItem.getItemName(), this.itemName);
        this.price =  createOrderItem.getPrice();
        this.qty = createOrderItem.getQty();

        return this;
    }
}