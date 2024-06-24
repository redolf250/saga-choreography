package com.redolf.order.service;

import com.redolf.application.dto.OrderRequest;
import com.redolf.order.entity.PurchaseOrder;
import com.redolf.application.event.OrderStatus;
import com.redolf.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderStatusPublisher statusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequest request){
        final PurchaseOrder order = repository.save(convertor(request));
        request.setOrderId(order.getId());
        statusPublisher.publishOrderEvent(request,OrderStatus.ORDER_CREATED);
        return order;
    }

    public List<PurchaseOrder> getOrders(){
        return repository.findAll();
    }

    private PurchaseOrder convertor(OrderRequest request){
        return PurchaseOrder.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .amount(request.getAmount())
                .orderStatus(OrderStatus.ORDER_CREATED)
                .build();
    }

}
