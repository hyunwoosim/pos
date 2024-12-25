package com.mulook.pos.controller;

import com.mulook.pos.Service.OrderService;
import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.entity.DiningTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/add")
    public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {

        orderDto.getOrderItems().forEach(orderItem -> {
            System.out.println("Item ID: " + orderItem.getItemId());
            System.out.println("Count: " + orderItem.getCount());
            System.out.println("Total Price: " + orderItem.getTotalPrice());
        });


        orderService.orderAdd(orderDto);

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderTable")
    public String showOrderTable(Model model) {

        List<DiningTable> diningTables = orderService.allOrders();
        System.out.println("########## controller ##############");
        System.out.println("diningTables.toString() = " + diningTables.toString() + "\n");
        System.out.println("########## controller ##############");

        model.addAttribute("diningTables", diningTables);

        return "/orders/orderTable.html";
    }
}