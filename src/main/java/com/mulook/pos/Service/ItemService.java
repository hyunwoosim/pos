package com.mulook.pos.Service;

import com.mulook.pos.dto.ItemDto;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Member;
import com.mulook.pos.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void createItem(ItemDto itemDto) {

        Item item = new Item();
        item.createItem(itemDto.getName(), itemDto.getPrice(), itemDto.getImgUrl(),
                        itemDto.getItemType());

        System.out.println("###### ItemService-createItem#########");
        System.out.println("item.getName() = " + item.getName());
        System.out.println("item.getPrice() = " + item.getPrice());
        System.out.println("item.getImgUrl() = " + item.getImgUrl());
        System.out.println("item.getItemType() = " + item.getItemType());
        System.out.println("###### ItemService-createItem#########");

        itemRepository.save(item);
    }

    public List<ItemDto> adminMenu() {

        return itemRepository.findItemWithoutImg();
    }

    public List<Item> userMenu() {

        return itemRepository.findAll();
    }



}