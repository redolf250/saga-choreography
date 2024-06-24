package com.redolf.order.entity;


import com.redolf.application.event.OrderStatus;
import com.redolf.application.event.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_purchase_orders")
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer amount;
    private Integer productId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
