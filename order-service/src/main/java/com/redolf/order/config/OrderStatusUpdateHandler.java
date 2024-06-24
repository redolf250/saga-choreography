package com.redolf.order.config;

import com.redolf.application.dto.OrderRequest;
import com.redolf.application.dto.PaymentRequest;
import com.redolf.application.event.OrderStatus;
import com.redolf.application.event.PaymentEvent;
import com.redolf.application.event.PaymentStatus;
import com.redolf.order.entity.PurchaseOrder;
import com.redolf.order.repository.OrderRepository;
import com.redolf.order.service.OrderStatusPublisher;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher statusPublisher;

    @Transactional
    public void updateOrder(int orderId, Consumer<PurchaseOrder> orderConsumer){
        orderRepository.findById(orderId)
                .ifPresent(orderConsumer.andThen(this::updateOrder));
    }

    public PaymentStatus value;
    @Bean
    public  Consumer<PaymentEvent> paymentConsumer(){
       return paymentEvent -> {
           value=paymentEvent.getPaymentStatus();
       };
    }

    public void updateOrder(PurchaseOrder purchaseOrder) {
         boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(value);
         OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELED;
        purchaseOrder.setOrderStatus(orderStatus);
        purchaseOrder.setPaymentStatus(value);
        System.out.println("=========================="+value);
        System.out.println("=========  isPaymentComplete =========== "+isPaymentComplete);
        System.out.println("=========  OrderStatus =========== "+orderStatus);
        if (!isPaymentComplete){
            statusPublisher.publishOrderEvent(mapRequest(purchaseOrder),orderStatus);
        }
    }

    private OrderRequest mapRequest(@NotNull PurchaseOrder purchaseOrder){
        OrderRequest request = new OrderRequest();
                request.setOrderId(purchaseOrder.getId());
                request.setAmount(purchaseOrder.getAmount());
                request.setProductId(purchaseOrder.getProductId());
                request.setUserId(purchaseOrder.getUserId());
                return  request;
    }
}
