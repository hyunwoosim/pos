package com.mulook.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private Long itemId;

    private int totalPrice;

    private int count;

    // ==  테스트 코드를 위해 생성//
    public OrderItemDto(Long itemId, int totalPrice, int count) {
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.count = count;
    }
}