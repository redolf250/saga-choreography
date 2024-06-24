package com.redolf.application.dto;


import com.redolf.application.event.OrderStatus;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer userId;
    private Integer orderId;
    private Integer price;
    private Integer productId;
    private OrderStatus orderStatus;
}
