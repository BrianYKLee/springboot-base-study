package com.study.base.boot.aggregations.v1.order.application;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.entity.OrderItemEntity;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    //@RequiredArgsConstructor -생성자를 만들어주는  역할 final은 무조건 초기값세팅이 되어야하는데 final이 달린애들을 생성자를 만들어
    // 줌.
    //@Autowired bean을 setter로도 주입해줌.
    private final OrderRepository orderRepository;//여기서 save를 지원해줌.

    public void create(CreateOrder createOrder) {
        final var orderAggregate = OrderAggregate.builder()
                .build()
                .patch(createOrder)
                .create(orderRepository);
        orderRepository.save(orderAggregate);
    }

    @Transactional
    public List<Long> createOrders(List<CreateOrder> orderList){
        final List<OrderAggregate>  orders = orderList.stream()
                                            .map(order  -> OrderAggregate.builder()
                                                        .build()
                                                        .patch(order)
                                            ).toList();

        List<OrderAggregate> saveOrders = orderRepository.saveAll(orders);
        return orderRepository.saveAll(orders).stream().map(OrderAggregate::getId).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public OrderAggregate get(long id){
        Optional<OrderAggregate> byId = orderRepository.findById(id);
        OrderAggregate orderAggregate = byId.orElseGet(null);// orElseThrow로 없는건 다 던지는것도 좋은 방법같다.

        List<OrderItemEntity> items= orderAggregate.getItems();
        //JPA의 레이지 로딩 시점은 get Method의 시점이 아니라 정확히 연관관계가 맺어진 변수를 사용할때이다.

        return orderAggregate;
    }
//propagation

    public Page<OrderAggregate> listByStatus(OrderStatusEnum orderStatus, Pageable pageable){

        Page<OrderAggregate> allByStatus =  orderRepository.findAllByStatus(orderStatus, pageable);

        return allByStatus;
    }

    @Transactional(readOnly = true)
    public Page<OrderAggregate> findAllByCreatedDateBetweenAndPriceBetween( LocalDateTime periodFrom,
                                                                            LocalDateTime periodTo,
                                                int minPrice,
                                                int maxPrice,
                                                Pageable pageable ){
        Page<OrderAggregate> allListByCondition =  orderRepository.findAllByCreatedDateBetweenAndPriceBetween(periodFrom,
                                                                                    periodTo,
                                                                                    minPrice,
                                                                                    maxPrice,
                                                                                    pageable);

        return allListByCondition;
    }


}
