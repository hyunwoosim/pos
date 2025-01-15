package com.mulook.pos.Service;

import com.mulook.pos.dto.DiningTableDto;
import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.dto.OrderItemDto;
import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.entity.OrderItem;
import com.mulook.pos.repository.DiningTableRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiningTableService {

    private final DiningTableRepository diningTableRepository;

    @Transactional
    public List<DiningTable> allTableOrder() {
        return diningTableRepository.findAll();
    }

    public DiningTableDto findTableOrder(int name) {

        DiningTable byName = diningTableRepository.findByName(name);

        DiningTableDto diningTableDto = new DiningTableDto();
        diningTableDto.setId(byName.getId());
        diningTableDto.setName(byName.getName());

        List<OrderDto> orders = byName.getOrders().stream()
                .map(order ->{
                    OrderDto orderDtos = new OrderDto();
                    orderDtos.setId(order.getId());
                    System.out.println("###############DiningTableService################");
                    System.out.println("orderDtos.getId() = " + orderDtos.getId());
                    System.out.println("###############DiningTableService################");

                List<OrderItemDto> items = order.getOrderItems().stream()
                    .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getItem().getId(),
                        item.getOrder().getId(),
                        item.getItem().getName(),
                        item.getItem().getPrice(),
                        item.getCount()
                    ))
                    .collect(Collectors.toList());
                    orderDtos.setOrderItems(items);

                    int totalOrderPrice = items.stream()
                        .mapToInt(item -> item.getPrice() * item.getCount())
                        .sum();
                    orderDtos.setTotalOrderPrice(totalOrderPrice);


                    return orderDtos;

                })
            .collect(Collectors.toList());
        int totalDiningTablePrice = orders.stream()
            .mapToInt(OrderDto::getTotalOrderPrice) // 각 OrderDto의 총 가격을 합산
            .sum();
        diningTableDto.setOrders(orders);
        diningTableDto.setTotalDiningTablePrice(totalDiningTablePrice);


        System.out.println("###############DiningTableService################");
        System.out.println("byName = " + byName);
        System.out.println("###############DiningTableService################");
        System.out.println("diningTableDto = " + diningTableDto);
        System.out.println("###############DiningTableService################");
        return diningTableDto;
    }

}