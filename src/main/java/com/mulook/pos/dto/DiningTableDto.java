package com.mulook.pos.dto;

import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.entity.OrderItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiningTableDto {

    private Long id;

    private int name;

    private List<OrderDto> orders;
    private int totalDiningTablePrice;

    public DiningTableDto() {
    }

    public DiningTableDto(Long id, int name, List<OrderDto> orders, int totalDiningTablePrice) {
        this.id = id;
        this.name = name;
        this.orders = orders;
        this.totalDiningTablePrice = totalDiningTablePrice;
    }



    public DiningTableDto(Long id, int name, List<OrderDto> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }


//
//    public static DiningTableDto from(DiningTable diningTable) {
//        List<OrderDto> orderDtos = diningTable.getOrders().stream()
//            .map(OrderDto::from)
//            .collect(Collectors.toList());
//
//        // 모든 Order의 총 금액 합산
//        int totalDiningTablePrice = orderDtos.stream()
//            .mapToInt(OrderDto::getTotalOrderPrice)
//            .sum();
//
//        System.out.println("########## DiningTableDto ###############");
//        System.out.println("totalDiningTablePrice = " + totalDiningTablePrice);
//        System.out.println("########## DiningTableDto ###############");
//
//
//        return new DiningTableDto(diningTable.getId(), diningTable.getName(), orderDtos, totalDiningTablePrice);
//    }


    @Override
    public String toString() {
        return "DiningTableDto{" +
            "id=" + id +
            ", name=" + name +
            ", orders=" + orders +
            '}';
    }
}