package com.mulook.pos.Service;

import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.entity.Order;
import com.mulook.pos.repository.OrderRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void orderAdd(OrderDto orderDto) {
//
//        Order order = new Order();
//        order.orderAdd(orderDto.getId(), orderDto.getCount(), orderDto.getTotalPrice(),
//                       LocalDateTime.now(),orderDto.getItems());
    }

}