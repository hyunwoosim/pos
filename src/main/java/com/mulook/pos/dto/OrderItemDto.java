package com.mulook.pos.dto;

import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.OrderItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class OrderItemDto {

    private Long orderItemId;

    private Long itemId;
    private Long orderId;

    private int totalPrice;

    private int count;

    private String name;
    private int price;

    private ItemDto itemDtos;

    public OrderItemDto() {
    }

    public OrderItemDto(Long itemId, int totalPrice, int count, ItemDto itemDtos) {
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.count = count;
        this.itemDtos = itemDtos;
    }

    public OrderItemDto(Long orderItemId ,Long itemId, Long orderId, String name, int price, int count) {
        this.orderItemId = orderItemId;
        this.itemId = itemId;
        this.orderId = orderId;
        this.name = name;
        this.price = price;
        this.count = count;
    }



    @Override
    public String toString() {
        return "OrderItemDto{" +
            "orderItemId=" + orderItemId +
            ", itemId=" + itemId +
            ", orderId=" + orderId +
            ", totalPrice=" + totalPrice +
            ", count=" + count +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", itemDtos=" + itemDtos +
            '}';
    }
}