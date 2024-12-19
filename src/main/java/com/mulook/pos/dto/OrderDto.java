package com.mulook.pos.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {


    private Long id;
    private int count;
    private int totalPrice;
    private LocalDateTime created;
    private List<ItemDto> items;

    public OrderDto(Long id, int count, int totalPrice, LocalDateTime created,
        List<ItemDto> items) {
        this.id = id;
        this.count = count;
        this.totalPrice = totalPrice;
        this.created = created;
        this.items = items;
    }
}