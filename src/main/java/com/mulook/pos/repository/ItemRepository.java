package com.mulook.pos.repository;

import com.mulook.pos.dto.ItemDto;
import com.mulook.pos.entity.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item,Long> {

    // 이미지를 제외한 아이템 받기 adminMenu에 사용함
    @Query("select new com.mulook.pos.dto.ItemDto(i.id, i.name, i.price, i.itemType) From Item i")
        List<ItemDto> findItemWithoutImg();
}