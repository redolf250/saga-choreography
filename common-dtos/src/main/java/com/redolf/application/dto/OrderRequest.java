package com.redolf.application.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer userId;
    private Integer orderId;
    private Integer amount;
    private Integer productId;
}
