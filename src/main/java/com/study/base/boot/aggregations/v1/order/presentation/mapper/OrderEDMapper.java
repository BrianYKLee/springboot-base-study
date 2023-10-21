package com.study.base.boot.aggregations.v1.order.presentation.mapper;

import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.presentation.dto.res.OrderDto;
import com.study.base.boot.config.mapstruct.base.BaseEntity;
import com.study.base.boot.config.mapstruct.mapper.SupportEntityToDtoMapper;
import jakarta.persistence.criteria.Order;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)//null인경우 매핑안함
public interface OrderEDMapper extends SupportEntityToDtoMapper<OrderAggregate, OrderDto> {

   /* @Condition
    default  boolean isLazyLoaded(List<? extends BaseEntity> entities){
        return isLoaded(entities);
    }*/
}
