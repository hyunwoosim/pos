package com.mulook.pos.controller;

import com.mulook.pos.Service.OrderService;
import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.dto.OrderItemDto;
import com.mulook.pos.entity.DiningTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/add")
    public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {


        if (orderDto.getCancelOrderId() != null) {
            orderService.orderItemDelete(orderDto);
        }

        if (!orderDto.getOrderItems().isEmpty()) {
            List<OrderItemDto> addItems = new ArrayList<>();
            List<OrderItemDto> updateItems = new ArrayList<>();
            splitOrderItems(orderDto, addItems, updateItems);

            if (!addItems.isEmpty()) {
                orderService.orderAdd(orderDto, addItems);
            }
            if (!updateItems.isEmpty()) {
                orderService.orderUpdate(orderDto, updateItems);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/orderTable");
        return ResponseEntity.ok(response);
    }


    // 유효성 검사를 통해 addItems,updateItems에 뎉이터를 넣고 있다.
    private void splitOrderItems(OrderDto orderDto, List<OrderItemDto> addItems, List<OrderItemDto> updateItems) {
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            if (orderItem.getOrderId() == null) {
                addItems.add(orderItem);
            } else {
                updateItems.add(orderItem);
            }
        }
    }
}