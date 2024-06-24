package com.redolf.application.event;

import com.redolf.application.dto.PaymentRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentEvent implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();

    private PaymentRequest paymentRequest;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequest paymentRequest, PaymentStatus paymentStatus) {
        this.paymentRequest = paymentRequest;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }
}
