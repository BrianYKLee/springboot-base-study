package com.study.base.boot.aggregations.v1.order.presentation;

import com.study.base.boot.config.annotations.RestAPI;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestAPI("/v1/orders")
public class OrderController {

    @GetMapping
    public List<String> getOrders(){
        return List.of("A", "B", "C");
    }
    @GetMapping
    private List<String> createOrders(List<String> orderReq){

        return orderReq;
    }
}
