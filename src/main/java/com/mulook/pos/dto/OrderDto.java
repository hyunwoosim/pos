package com.mulook.pos.dto;

import com.mulook.pos.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {


    private Long id;
    private int diningName;

    private LocalDateTime created;
    private List<OrderItemDto> orderItems;
    private int totalOrderPrice;

    private Map<Long, List<Long>> cancelOrderId;

    public OrderDto() {
    }

    public OrderDto(Long id) {
        this.id = id;
    }

    public OrderDto(int diningName) {
        this.diningName = diningName;
    }



    public OrderDto(LocalDateTime created, List<OrderItemDto> orderItems) {
        this.created = created;
        this.orderItems = orderItems;
    }

    public OrderDto(int diningName, LocalDateTime created, List<OrderItemDto> orderItems) {
        this.diningName = diningName;
        this.created = created;
        this.orderItems = orderItems;
    }

    public OrderDto(Long id, LocalDateTime created, List<OrderItemDto> orderItems) {
        this.id = id;
        this.created = created;
        this.orderItems = orderItems;
    }

    public OrderDto(Long id,LocalDateTime created, List<OrderItemDto> orderItems,
        int totalOrderPrice) {
        this.id = id;
        this.created = created;
        this.orderItems = orderItems;
        this.totalOrderPrice = totalOrderPrice;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
            "id=" + id +
            ", diningName=" + diningName +
            ", created=" + created +
            ", orderItems=" + orderItems +
            ", totalOrderPrice=" + totalOrderPrice +
            ", cancelOrderId=" + cancelOrderId +
            '}';
    }


}