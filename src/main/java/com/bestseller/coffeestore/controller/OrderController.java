package com.bestseller.coffeestore.controller;

import com.bestseller.coffeestore.controller.bean.FinalizeOrder;
import com.bestseller.coffeestore.model.OrderSummary;
import com.bestseller.coffeestore.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/finalizeOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderSummary> finalizeOrder(
            @RequestBody FinalizeOrder finalizeOrder
    ) {
        if (finalizeOrder == null) {
            // throw Exception (a custom exception could be created here...)
        }
        return ResponseEntity.ok(orderService.finalizeOrder(finalizeOrder));
    }

}
