package com.mulook.pos.Service;

import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Order;
import com.mulook.pos.entity.OrderItem;
import com.mulook.pos.repository.DiningTableRepository;
import com.mulook.pos.repository.ItemRepository;
import com.mulook.pos.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DiningTableRepository diningTableRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void orderAdd(OrderDto orderDto) {

        DiningTable diningTable = diningTableRepository.findByName(orderDto.getDiningName());


        System.out.println("########OrderService1################");

        System.out.println("########OrderService1################");
        System.out.println("       ");

        Order order = new Order();
        order.addOrder(diningTable,LocalDateTime.now());

        System.out.println("########OrderService22################");
        System.out.println(" order.getCreated() = " + order.getCreated());
        System.out.println("########OrderService22################");
        System.out.println("       ");

        orderDto.getOrderItems().forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();



            OrderItem orderItem = new OrderItem();
            orderItem.addOrderItem(item, orderItemDto.getTotalPrice(),orderItemDto.getCount());

            System.out.println("########OrderService33################");
            System.out.println("item = " + item);
            System.out.println("orderItemDto.getTotalPrice() = " + orderItemDto.getTotalPrice());
            System.out.println("orderItemDto.getCount() = " + orderItemDto.getCount());
            System.out.println("########OrderService333################");
            System.out.println("       ");

            order.addOrderItem(orderItem);
        });

        orderRepository.save(order);
    }


}