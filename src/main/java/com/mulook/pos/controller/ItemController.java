package com.mulook.pos.controller;

import com.mulook.pos.Service.DiningTableService;
import com.mulook.pos.Service.ItemService;
import com.mulook.pos.Service.ValidateHandlingService;
import com.mulook.pos.Service.aws.S3Service;
import com.mulook.pos.dto.DiningTableDto;
import com.mulook.pos.dto.ItemDto;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Enum.ItemType;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ValidateHandlingService validateHandlingService;
    private final S3Service s3Service;
    private final DiningTableService diningService;


    @GetMapping("/item/new")
    public String createItemForm(Model model) {
        model.addAttribute("itemDto", new ItemDto());

        model.addAttribute("itemType", ItemType.values());

        return "items/newItem.html";
    }

    @PostMapping("/item/createItem")
    public String createItem(@Valid ItemDto itemDto, BindingResult result, Model model) {

        // 에러에 맞게 문구 나가기
        if (result.hasErrors()) {
            Map<String, String> validatorResult = validateHandlingService.validateHandling(result);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "items/newItem.html";
        }

        itemService.createItem(itemDto);

        return "redirect:/item/userMenu";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("menu/" + filename);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("/item/adminMenu/{name}")
    public String adminMenu(Model model, @PathVariable int name) {

        List<ItemDto> itemDto = itemService.adminMenu();

        Map<ItemType, List<ItemDto>> itemTypeListMap = itemDto.stream()
            .collect(Collectors.groupingBy(ItemDto::getItemType));

        // 현재 테이블의 주문 정보
        DiningTableDto currentOrder = diningService.findTableOrder(name);

        System.out.println("##############Cotroller###########");
        System.out.println("currentOrder = " + currentOrder.toString());
        System.out.println("##############Cotroller###########");
        System.out.println("===================================");
        System.out.println("##############Cotroller###########");
        System.out.println("currentOrder = " + currentOrder.getTotalDiningTablePrice());
        System.out.println("##############Cotroller###########");


        model.addAttribute("itemTypeListMap", itemTypeListMap);
        model.addAttribute("tableName", name);
        model.addAttribute("currentOrder", currentOrder);
        model.addAttribute("getTotalDiningTablePrice", currentOrder.getTotalDiningTablePrice());

        return "items/adminMenu.html";
    }


    @GetMapping("/item/userMenu")
    public String userMenu(Model model) {

        List<Item> userItem = itemService.userMenu();

        Map<ItemType, List<Item>> itemTypeListMap = userItem.stream()
            .collect(Collectors.groupingBy(Item::getItemType));

        model.addAttribute("itemTypeListMap", itemTypeListMap);

        return "items/userMenu.html";
    }
}