package com.study.base.boot.aggregations.v1.order.application;

import com.study.base.boot.aggregations.v1.order.application.dto.req.CreateOrder;
import com.study.base.boot.aggregations.v1.order.application.dto.req.GetOrder;
import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.entity.OrderItemEntity;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.OrderRepository;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.req.OrderCondition;
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

    @Transactional(readOnly = true)
    public Page<OrderAggregate> list(GetOrder request){

        final OrderCondition condition = request.toCondition();
        final  Page<OrderAggregate> pageOrders = orderRepository.getOrders(condition);
        return pageOrders;
    }

    //주문 상태 변경 메소드 생성
    @Transactional
    public void changeStatus(long id, OrderStatusEnum status){
      orderRepository.changeStatus(id, status);

        /*  switch (status) {
            case ORDER -> this.changeToOrder(id);
            case CANCELED ->  this.changeToCanceled(id);
            default ->  throw new IllegalArgumentException("Wrong Status");
        }*/

    }

    @Transactional
    public void changeToCanceled(long id){
        final var orderOptional  = orderRepository.findById(id);

        orderOptional.ifPresent(OrderAggregate::changeCanceled);
    }
    @Transactional
    public void changeToOrder(long id){
        final var orderOptional = orderRepository.findById(id);

        orderOptional.ifPresent(order -> {
            order.changeOrder();
        });

        /*// 배치 : 보통 100개에서 많으면 500개정도까지가 좋다. 개수를 끊어서 조회한다. 쿼리 성능이 더 좋아짐
        배치는 모수가 클때 많이사용

        JSON Web Token(JWT) 구조

        HEADER(암호화 알고리즘,토큰타입).PAYLOAD.SIGNATURE

        1. header.payload, signature로 구분
        2. 마침표를 기준으로 구분
        3. 각 영역의 json의 구조를 가진다.
        4.토큰내에 정보가 있어 상태에 대한 정보를 따로 저장하지 않아도 된다ㅣ.
        5. 토큰 탈취시 누구든지 호출할 수 있다.
        6. 토큰 탈취에 대한 대안책 강구 필요-> 보통 refresh token 많이 사용

        redis-> 데이터베이스 (NoSQL)  휘발성으로 단기간 저장해서 속도가 빠르다

        jwt-> 모든 서버에서 동일하게사용가능, 상태를 저장하지 않기 떄문에 중복로그인 처리가 힘듬, 토큰 만료시 갱신안하면 로그인이 풀리는 이슈발생

        만료시간의 길이(용도) 차리로 일반 jwt와 refresh token을 구분

        session-> 서버별 세션이 생성, 상태를 서버의 세션에 저장하고 있기 떄문에 중복로그인 처리가 좋음, 서버 배포시 세션 클러스터링을 통해 배포되는 서버에 접속된 유저의 로그인이 유지되어야함

         ** 세션 클러스터링: 2대 이상의 WAS 또는 서버를 사용할때 로드 밸런싱 장애 대비 등 세션을 공유하는 것

        payload를 비대칭키 암호화를 한번더 하는 경우도 있다.-> 토큰 탈취시 해석을 못하게 하기위해
        */
    }
}
