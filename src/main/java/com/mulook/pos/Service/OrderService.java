package com.mulook.pos.Service;

import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Member;
import com.mulook.pos.entity.Order;
import com.mulook.pos.entity.OrderItem;
import com.mulook.pos.repository.ItemRepository;
import com.mulook.pos.repository.MemberRepository;
import com.mulook.pos.repository.OrderRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void orderAdd(OrderDto orderDto) {

        Member member = memberRepository.findById(orderDto.getMemberId()).orElseThrow();

        Order order = new Order();
        order.addOrder(member,LocalDateTime.now());

        orderDto.getOrderItems().forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();

            OrderItem orderItem = new OrderItem();
            orderItem.addOrderItem(item, orderItemDto.getTotalPrice(),orderItemDto.getCount());

            order.addOrderItem(orderItem);
        });

        orderRepository.save(order);
    }

}