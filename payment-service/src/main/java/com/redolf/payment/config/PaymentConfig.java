package com.redolf.payment.config;

import com.redolf.application.event.OrderEvent;
import com.redolf.application.event.OrderStatus;
import com.redolf.application.event.PaymentEvent;
import com.redolf.payment.service.PaymentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConfig {

    @Autowired
    private PaymentService service;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor(){
        return  orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    public Mono<PaymentEvent> processPayment(@NotNull OrderEvent orderEvent) {
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            return Mono.fromSupplier(() -> this.service.newOderEvent(orderEvent));
        }else{
            return Mono.fromRunnable(() -> this.service.cancelOderEvent(orderEvent));
        }
    }
}
