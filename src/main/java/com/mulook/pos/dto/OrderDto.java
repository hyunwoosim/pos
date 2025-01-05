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
    private int totalOrderPrice;

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
            '}';
    }
//
//
//    public static OrderDto from(Order order) {
//        // Order에서 OrderItemDto 리스트를 생성
//        List<OrderItemDto> orderItemDtos = OrderItemDto.fromList(order.getOrderItems());
//
//
//
//            // OrderItem의 총 가격 합산
//            int totalOrderPrice = orderItemDtos.stream()
//                .mapToInt(orderItemDto -> orderItemDto.getTotalPrice())
//                .sum();
//
//        System.out.println("########## OrderDTO ###############");
//        System.out.println("totalOrderPrice = " + totalOrderPrice);
//        System.out.println("########## OrderDTO ###############");
//
//
//        // 변환된 데이터를 사용하여 OrderDto 객체를 생성하고 반환
//        return new OrderDto(order.getId(), order.getCreated(), orderItemDtos, totalOrderPrice);
//    }
}