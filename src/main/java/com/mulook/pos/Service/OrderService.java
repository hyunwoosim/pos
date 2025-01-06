package com.mulook.pos.Service;

import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.dto.OrderItemDto;
import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Order;
import com.mulook.pos.entity.OrderItem;
import com.mulook.pos.repository.DiningTableRepository;
import com.mulook.pos.repository.ItemRepository;
import com.mulook.pos.repository.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void orderAdd(OrderDto orderDto, List<OrderItemDto> addItems) {

        System.out.println("####### orderAdd #######");
        System.out.println("orderDto = " + orderDto.getId());
        System.out.println("####### orderAdd #######");

        DiningTable diningTable = diningTableRepository.findByName(orderDto.getDiningName());

        Order order = new Order();
        order.addDining(diningTable);

        addItems.forEach(orderItemDto -> {
            Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();

            OrderItem orderItem = new OrderItem();
            orderItem.addOrderItem(item, orderItemDto.getTotalPrice(),orderItemDto.getCount());

            order.addOrderItem(orderItem);
        });

        orderRepository.save(order);
    }

    @Transactional
    public void orderUpdate(OrderDto orderDto, List<OrderItemDto> updateItems) {


        System.out.println("####### orderUpdate #######");
        System.out.println("orderDto = " + orderDto.getId());
        System.out.println("####### orderUpdate #######");

        // 1. 업데이트 항목에 해당하는 orderItemId들을 기준으로 각 항목을 업데이트
        updateItems.forEach(orderItemDto -> {
            // updateItems에서 각 항목을 순차적으로 처리
            OrderItem existingOrderItem = orderItemRepository.findById(orderItemDto.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 항목입니다."));

            // 기존 OrderItem의 변경 감지를 트리거하기 위해 값 설정
            existingOrderItem.updateOrderItem(orderItemDto.getTotalPrice(), orderItemDto.getCount());

            // 해당 OrderItem을 포함하는 Order를 찾아서 변경된 값을 반영
            Order order = existingOrderItem.getOrder();

            System.out.println("####### orderUpdate22 #######");
            System.out.println("order = " + order);
            System.out.println("####### orderUpdate 22#######");

            orderRepository.save(order);  // 변경된 주문을 저장 (변경 감지)
        });

    }

}