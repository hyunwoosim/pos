package com.mulook.pos.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {


    private int diningName;

    private List<OrderItemDto> orderItems;

    private LocalDateTime created;


}