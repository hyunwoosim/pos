package com.mulook.pos.dto;

import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.ItemType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Long id;

    @NotEmpty(message = "픔목명은 필수 입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다")
    private int price;

    private String imgUrl;

    @NotNull
    private ItemType itemType;

    public ItemDto() {
    }

    // itemRepository.findItemWithoutImg()에서 사용할 생성자
    public ItemDto(Long id, String name, int price, ItemType itemType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }



    @Override
    public String toString() {
        return "ItemDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +

            '}';
    }


}