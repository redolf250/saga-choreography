package com.redolf.payment.service;

import com.redolf.application.dto.OrderRequest;
import com.redolf.application.dto.PaymentRequest;
import com.redolf.application.event.OrderEvent;
import com.redolf.application.event.PaymentEvent;
import com.redolf.application.event.PaymentStatus;
import com.redolf.payment.entity.UserBalance;
import com.redolf.payment.entity.UserTransaction;
import com.redolf.payment.repository.UserBalanceRepository;
import com.redolf.payment.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository balanceRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

//    @PostConstruct
//    public void initUserBalance(){
//        final Stream<UserBalance> userBalanceStream = Stream.of(
//                new UserBalance(101,5000),
//                new UserBalance(102,3000),
//                new UserBalance(103,4200),
//                new UserBalance(104,20000),
//                new UserBalance(105,999));
//        balanceRepository.saveAll(userBalanceStream.toList());
//    }

    @Transactional
    public PaymentEvent newOderEvent(OrderEvent orderEvent) {
        final OrderRequest orderRequest = orderEvent.getOrderRequest();
        PaymentRequest paymentRequest = makePaymentRequest(orderRequest);
        return balanceRepository.findById(orderRequest.getUserId())
                .filter(ub -> ub.getAmount() > orderRequest.getAmount())
                .map(getUserBalancePaymentEventFunction(orderRequest, paymentRequest))
                .orElse(new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_FAILED));
    }

    @NotNull
    private Function<UserBalance, PaymentEvent> getUserBalancePaymentEventFunction(OrderRequest orderRequest, PaymentRequest paymentRequest) {
        return ub -> {
            ub.setAmount(ub.getAmount() - orderRequest.getAmount());
            transactionRepository.save(new UserTransaction(orderRequest.getOrderId(),
                    orderRequest.getUserId(),
                    orderRequest.getAmount()));
            return new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_COMPLETED);
        };
    }

    @Transactional
    public void cancelOderEvent(OrderEvent orderEvent) {
        transactionRepository.findById(orderEvent.getOrderRequest().getUserId())
                .ifPresent(ut -> {
                   transactionRepository.delete(ut);
                    transactionRepository.findById(ut.getUserId())
                           .ifPresent(ub -> ub.setAmount(ub.getAmount() + ut.getAmount()));
                });
    }

    @NotNull
    private PaymentRequest makePaymentRequest(@NotNull OrderRequest orderRequest){
        return new PaymentRequest(orderRequest.getUserId(),orderRequest.getOrderId(),orderRequest.getAmount());
    }

    public List<UserTransaction> getUserTransaction(int id) {
        return transactionRepository.findAllById(Collections.singleton(id));
    }
}
