package com.redolf.order.controller;

import com.redolf.application.dto.OrderRequest;
import com.redolf.order.entity.PurchaseOrder;
import com.redolf.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequest request){
        return service.createOrder(request);
    }

    @GetMapping("/")
    public List<PurchaseOrder> getOrders(){
        return service.getOrders();
    }
}
