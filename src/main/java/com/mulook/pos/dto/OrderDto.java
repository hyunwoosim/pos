package com.mulook.pos.dto;

import com.mulook.pos.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public String toString() {
        return "OrderDto{" +
            "diningName=" + diningName +
            ", created=" + created +
            ", orderItems=" + orderItems +
            '}';
    }


    // Order 엔티티를 OrderDto로 변환하는 from 메서드
    public static OrderDto from(Order order) {
        // Order에서 OrderItemDto 리스트를 생성
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
            .map(OrderItemDto::from)  // OrderItemDto로 변환
            .collect(Collectors.toList());

        // 변환된 데이터를 사용하여 OrderDto 객체를 생성하고 반환
        return new OrderDto(order.getId(), order.getCreated(), orderItemDtos);
    }
}