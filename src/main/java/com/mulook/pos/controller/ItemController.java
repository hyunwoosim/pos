package com.mulook.pos.controller;

import com.mulook.pos.Service.ItemService;
import com.mulook.pos.Service.ValidateHandlingService;
import com.mulook.pos.dto.ItemDto;
import com.mulook.pos.entity.ItemType;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ValidateHandlingService validateHandlingService;


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
}