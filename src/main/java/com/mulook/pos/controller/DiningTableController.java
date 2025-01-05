package com.mulook.pos.controller;

import com.mulook.pos.Service.DiningTableService;
import com.mulook.pos.entity.DiningTable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class DiningTableController {

private final DiningTableService diningService;

    @GetMapping("/orderTable")
    public String showOrderTable(Model model) {

        List<DiningTable> diningTables = diningService.allTableOrder();


        model.addAttribute("diningTables", diningTables);

        return "/diningTables/orderTable.html";
    }


}