package com.redolf.payment.controller;

import com.redolf.payment.entity.UserTransaction;
import com.redolf.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/{user-id}")
    private ResponseEntity<?> getPayments(@PathVariable(name = "user-id") int id){
        List<UserTransaction> userTransaction = service.getUserTransaction(id);
        return new ResponseEntity<>(userTransaction, HttpStatus.OK);
    }
}
