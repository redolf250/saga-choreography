package com.redolf.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user_transaction")
public class UserTransaction {
    @Id
    @GeneratedValue
    private int orderId;
    private int userId;
    private int amount;
}
