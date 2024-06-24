package com.redolf.order.config;

import com.redolf.application.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PaymentEventConfig {

    @Autowired
    private OrderStatusUpdateHandler handler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        return paymentEvent -> handler.updateOrder(paymentEvent.getPaymentRequest().getOrderId(),paymentEvent1 -> {
            paymentEvent1.setPaymentStatus(paymentEvent1.getPaymentStatus());
            System.out.println(paymentEvent);;
        });
    }

}
