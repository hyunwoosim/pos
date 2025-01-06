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

        List<OrderItemDto> addItems = new ArrayList<>();
        List<OrderItemDto> updateItems = new ArrayList<>();

        // orderDto의 orderItems를 순회하여 분리
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            if (orderItem.getOrderId() == null) {
                addItems.add(orderItem);  // orderId가 null인 항목은 추가
            } else {
                updateItems.add(orderItem);  // orderId가 null이 아닌 항목은 업데이트
            }
        }

        // 분리된 addItems와 updateItems를 각각 처리
        if (!addItems.isEmpty()) {
            System.out.println("########### 오더 컨틀롤러addItems#############");
            System.out.println("addItems = " + addItems);
            System.out.println("########### 오더 컨틀롤러addItems#############");
            System.out.println("===============================================");
            System.out.println("########### 오더 컨트롤러 orderDto#############");
            System.out.println("orderDto = " + orderDto);
            System.out.println("########### 오더 컨트롤러 orderDto#############");

            // 새로운 주문 항목을 추가하는 서비스 호출
            orderService.orderAdd(orderDto, addItems);
        }

        if (!updateItems.isEmpty()) {

            System.out.println("########### 오더 컨트롤러 updateItems#############");
            System.out.println("updateItems = " + updateItems);
            System.out.println("########### 오더 컨트롤러 updateItems#############");
            System.out.println("===============================================");
            System.out.println("########### 오더 컨트롤러 orderDto#############");
            System.out.println("orderDto = " + orderDto);
            System.out.println("########### 오더 컨트롤러 orderDto#############");

            // 기존 주문 항목을 업데이트하는 서비스 호출
            orderService.orderUpdate(orderDto, updateItems);
        }

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/orderTable");
        return ResponseEntity.ok(response);
    }


}