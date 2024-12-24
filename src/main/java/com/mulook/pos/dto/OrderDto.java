package com.mulook.pos.dto;

import com.mulook.pos.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {


    private Long memberId;

    private List<OrderItemDto> orderItems;

    private LocalDateTime created;


}