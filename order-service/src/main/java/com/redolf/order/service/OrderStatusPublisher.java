package com.redolf.order.service;

import com.redolf.application.dto.OrderRequest;
import com.redolf.application.event.OrderEvent;
import com.redolf.application.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequest request, OrderStatus status) {
        OrderEvent event = new OrderEvent(request, status);
        orderSinks.tryEmitNext(event);
    }
}
