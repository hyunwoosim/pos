package com.mulook.pos.controller;

import com.mulook.pos.Service.ItemService;
import com.mulook.pos.Service.ValidateHandlingService;
import com.mulook.pos.Service.aws.S3Service;
import com.mulook.pos.dto.ItemDto;
import com.mulook.pos.entity.ItemType;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ValidateHandlingService validateHandlingService;
    private final S3Service s3Service;


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

        return "redirect:/";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("menu/" + filename);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("/item/adminMenu")
    public String adminMenu(Model model) {

        List<ItemDto> itemDto = itemService.adminMenu();

        Map<ItemType, List<ItemDto>> itemTypeListMap = itemDto.stream()
            .collect(Collectors.groupingBy(ItemDto::getItemType));

        System.out.println("######## Controller-adminMenu#########");
        System.out.println("itemTypeListMap.keySet( = " + itemTypeListMap.keySet());
        System.out.println("itemTypeListMap.values() = " + itemTypeListMap.values());
        System.out.println("######## Controller-adminMenu#########");

        model.addAttribute("itemTypeListMap", itemTypeListMap);

        return "items/adminMenu.html";
    }
}