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

//    private OrderDto orderDto;
//    private OrderItemDto OrderItemDto;
//    private ItemDto itemDto;





    public DiningTableDto(Long id, int name, List<OrderDto> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    // 엔티티(DiningTable)를 DTO(DiningTableDto)로 변환하는 from 메서드
    public static DiningTableDto from(DiningTable diningTable) {
        // DiningTable에서 OrderDto 리스트를 생성
        List<OrderDto> orderDtos = diningTable.getOrders().stream()
            .map(OrderDto::from)  // OrderDto로 변환
            .collect(Collectors.toList());

        // 변환된 데이터를 사용하여 DiningTableDto 객체를 생성하고 반환
        return new DiningTableDto(diningTable.getId(), diningTable.getName(), orderDtos);
    }

    @Override
    public String toString() {
        return "DiningTableDto{" +
            "id=" + id +
            ", name=" + name +
            ", orders=" + orders +

            '}';
    }
}