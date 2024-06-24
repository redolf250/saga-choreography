package com.redolf.application.dto;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Integer userId;
    private Integer orderId;
    private Integer amount;
}
