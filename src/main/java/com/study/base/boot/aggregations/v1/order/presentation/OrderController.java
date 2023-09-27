package com.study.base.boot.aggregations.v1.order.presentation;

import com.study.base.boot.aggregations.v1.order.application.OrderService;
import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.entity.OrderItemEntity;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateOrderDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.req.CreateOrdersDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.res.OrderDto;
import com.study.base.boot.aggregations.v1.order.presentation.dto.res.OrderItemDto;
import com.study.base.boot.aggregations.v1.order.presentation.mapper.OrderEDMapper;
import com.study.base.boot.config.annotations.Get;
import com.study.base.boot.config.annotations.Post;
import com.study.base.boot.config.annotations.RestApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestApi("/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderEDMapper orderEDMapper;
/*    @Get
    public List<String> getOrders() {
        return List.of("A", "B", "C");
    }*/

    @Post
    public List<Long> createOrders(@RequestBody @Valid CreateOrdersDto request) throws Exception {
        final List<CreateOrder> createOrderList = request.getCreateOrders().stream().map(CreateOrderDto::toCreate).toList();
        return orderService.createOrders(createOrderList);
    }

    @Get("/{id}")
    public OrderDto getOrder(@PathVariable long id) {
        OrderAggregate orderAggregate = orderService.get(id);

        OrderDto orderDto = orderEDMapper.toDto(orderAggregate);

   /*     List<OrderItemEntity> items = orderAggregate.getItems();

       final var itemsDtos = items.stream()
               .map(item ->
                       OrderItemDto.builder()
                            .id(item.getId())
                            .itemId(item.getItemId())
                            .itemName(item.getItemName())
                            .status(item.getStatus())
                            .price(item.getPrice())
                            .qty(item.getQty())
                            .createdDate(item.getCreatedDate())
                            .updatedDate(item.getUpdatedDate())
                            .build())
                .collect(Collectors.toList());*/
        return orderDto;
    }
    //맵스트럭트(MapStruct) 자바 라이브러리 매핖ㅇ을 도와준다.

    @Get("/status/{status}")
    public Page<OrderDto> getOrders(
            @PathVariable OrderStatusEnum status,
            @PageableDefault(size =10, sort = "id", direction =Sort.Direction.DESC)Pageable pageable){
        Page<OrderAggregate> pageOrders  = orderService.listByStatus(status, pageable);
        List<OrderAggregate> orders = pageOrders.getContent();

        List<OrderDto> orderDtos  = orders.stream()
                .map(order -> orderEDMapper.toDto(order))
                .collect(Collectors.toList());

        return new PageImpl<>(orderDtos, pageable, pageOrders.getTotalElements());
        //jpa는 null이나온 케이스가 없어서 stream을 박아도 NPE는 안뜬다.
    }

    @Get("/req")
    public Page<OrderDto> getOrdersByReq(LocalDateTime periodFrom,
                                         LocalDateTime periodTo,
                                         int minPrice,
                                         int maxPrice,
                                         @PageableDefault(size =10, sort = "id", direction =Sort.Direction.DESC)Pageable pageable){
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Page<OrderAggregate> pageOrders  = orderService.findAllByCreatedDateBetweenAndPriceBetween(periodFrom, periodTo, minPrice, maxPrice, pageable );
        List<OrderAggregate> orders = pageOrders.getContent();

        List<OrderDto> orderDtos  = orders.stream()
                .map(order -> orderEDMapper.toDto(order))
                .toList();

        return new PageImpl<>(orderDtos, pageable, pageOrders.getTotalElements());
    }
}