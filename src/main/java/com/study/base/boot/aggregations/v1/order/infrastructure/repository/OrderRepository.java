package com.study.base.boot.aggregations.v1.order.infrastructure.repository;

import com.study.base.boot.aggregations.v1.order.domain.OrderAggregate;
import com.study.base.boot.aggregations.v1.order.domain.enumerations.OrderStatusEnum;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.req.OrderCondition;
import com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.res.OrderInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderAggregate, Long> {
    //@Repository를 달면 dB랑 연결해주는 bean으로 인식
    //JpaRepository를 사용할 서비스가 있어야함. orderService

    Page<OrderAggregate> findAllByStatus(OrderStatusEnum status, Pageable pageable);

    @Query
    Page<OrderAggregate> findAllByCreatedDateBetweenAndPriceBetween(@Param("periodFrom") LocalDateTime periodFrom,
                                            @Param("periodTo")LocalDateTime periodTo,
                                            @Param("minPr")int minPrice,
                                            @Param("maxPr")int maxPrice,
                                            Pageable pageable);

    Page<OrderAggregate> findAllByCreatedDateGreaterThanEqualAndCreatedDateLessThanAndPriceGreaterThanEqual(
            LocalDateTime startDate,
            LocalDateTime endDate,
            int price,
            Pageable pageable
    );

    default Page<OrderAggregate> getOrders(OrderCondition condition){
      return  getOrders(
              condition.getStartDate(),
              condition.getEndDate(),
              condition.getPrice(),
              condition.getPageable()
        );
    }

    @Query(value = """
           select order
           from OrderAggregate  order
           join fetch order.items
           where order.createdDate >= :startDate
           and order.createdDate < :endDate
           and order.price >= :price
           """)
    Page<OrderAggregate> getOrders(LocalDateTime startDate, LocalDateTime endDate, int price, Pageable pageable);


    @Query("""
        update OrderAggregate order
        set order.status = :status
        where order.id = :id
    """)
    @Modifying
    void changeStatus(long id, OrderStatusEnum status);

 /*   @Query(value = """
        select new com.study.base.boot.aggregations.v1.order.infrastructure.repository.dto.res.OrderInfoProjection(
            order.orderNumber,
            order.status,
            order.price,
            order.createdDate
        )
        from OrderAggregate order 
        where order.createdDate >= :startDate 
        and order.createdDate < :endDate
        and order.price >= :price
    """)
    List<OrderInfoProjection> getOrders(LocalDateTime startDate, LocalDateTime endDate, int price);*/

}
