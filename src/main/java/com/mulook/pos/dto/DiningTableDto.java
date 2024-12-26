package com.mulook.pos.dto;

import com.mulook.pos.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiningTableDto {

    private Long id;

    private int name;

    private OrderDto orderDto;
    private OrderItemDto OrderItemDto;
    private ItemDto itemDto;

    public DiningTableDto(Long id, int name, OrderDto orderDto,
     OrderItemDto orderItemDto, ItemDto itemDto) {
        this.id = id;
        this.name = name;
        this.orderDto = orderDto;
        this.OrderItemDto = orderItemDto;
        this.itemDto = itemDto;
    }
}