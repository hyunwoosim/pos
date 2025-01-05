package com.mulook.pos.Service;

import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.dto.OrderItemDto;
import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Order;
import com.mulook.pos.entity.OrderItem;
import com.mulook.pos.repository.DiningTableRepository;
import com.mulook.pos.repository.ItemRepository;
import com.mulook.pos.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DiningTableRepository diningTableRepository;
    private final ItemRepository itemRepository;

//    @Transactional
//    public void orderAdd(OrderDto orderDto) {
//
//        DiningTable diningTable = diningTableRepository.findByName(orderDto.getDiningName());
//
//        Order order = new Order();
//        order.addDining(diningTable);
//
//        orderDto.getOrderItems().forEach(orderItemDto -> {
//            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.addOrderItem(item, orderItemDto.getTotalPrice(),orderItemDto.getCount());
//
//            order.addOrderItem(orderItem);
//        });
//
//        orderRepository.save(order);
//    }

    @Transactional
    public void orderAdd(OrderDto orderDto) {
        // orderId가 존재하면 기존 주문을 업데이트, 없으면 새로운 주문을 생성
        Order order = null;

        System.out.println("#######OrderService 11 #######");
        System.out.println("orderDto = " + orderDto.getId());
        System.out.println("#######OrderService 11 #######");

        if (orderDto.getId() != null) {

            System.out.println("#######OrderService 22 #######");
            System.out.println("orderDto = " + orderDto.getId());
            System.out.println("#######OrderService 22 #######");

            // 기존 주문이 있을 경우, orderId로 주문을 찾아서 업데이트
            order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

            // 기존 주문을 업데이트
            updateExistingOrder(order, orderDto);
        } else {

            System.out.println("#######OrderService 33 #######");
            System.out.println("orderDto = " + orderDto.getId());
            System.out.println("#######OrderService 33 #######");
            // 새로운 주문이면 새로운 주문 생성
            DiningTable diningTable = diningTableRepository.findByName(orderDto.getDiningName());
            order = new Order();
            order.addDining(diningTable);

            // 새로운 주문 항목 추가
            Order finalOrder = order;
            orderDto.getOrderItems().forEach(orderItemDto -> {
                Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();
                OrderItem orderItem = new OrderItem();
                orderItem.addOrderItem(item, orderItemDto.getTotalPrice(), orderItemDto.getCount());
                finalOrder.addOrderItem(orderItem);
            });

            // 새로운 주문 저장
            orderRepository.save(order);
        }
    }

    private void updateExistingOrder(Order existingOrder, OrderDto orderDto) {

            // 1. 기존 주문 항목을 Map으로 변환 (orderItemId를 Key로 사용)
            Map<Long, OrderItem> existingOrderItemMap = existingOrder.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItem::getId, orderItem -> orderItem));

            // 2. 새로운 데이터의 orderItemId를 기준으로 기존 항목 수정
            orderDto.getOrderItems().forEach(orderItemDto -> {
                OrderItem existingOrderItem = existingOrderItemMap.get(orderItemDto.getOrderItemId());
                if (existingOrderItem != null) {
                    // 기존 OrderItem의 변경 감지를 트리거하기 위해 값 설정
                    existingOrderItem.updateOrderItem(orderItemDto.getTotalPrice(), orderItemDto.getCount());
                }
            });


        // 기존 주문을 저장하여 업데이트 (변경 감지)
        orderRepository.save(existingOrder);
    }


}