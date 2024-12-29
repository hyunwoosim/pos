package com.mulook.pos.dto;

import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.OrderItem;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class OrderItemDto {

    private Long itemId;

    private int totalPrice;

    private int count;

    private ItemDto itemDtos;

    public OrderItemDto(Long itemId, int totalPrice, int count, ItemDto itemDtos) {
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.count = count;
        this.itemDtos = itemDtos;
    }


    // OrderItem 엔티티를 OrderItemDto로 변환하는 from 메서드
    public static OrderItemDto from(OrderItem orderItem) {
        // Item 엔티티를 ItemDto로 변환
        ItemDto itemDto = ItemDto.from(orderItem.getItem());

        // 변환된 데이터를 사용하여 OrderItemDto 객체를 생성하고 반환
        return new OrderItemDto(orderItem.getId(), orderItem.getTotalPrice(), orderItem.getCount(), itemDto);
    }

    // ==  테스트 코드를 위해 생성//
    public OrderItemDto(Long itemId, int totalPrice, int count) {
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
            "itemId=" + itemId +
            ", totalPrice=" + totalPrice +
            ", count=" + count +
            ", itemDtos=" + itemDtos +
            '}';
    }
}