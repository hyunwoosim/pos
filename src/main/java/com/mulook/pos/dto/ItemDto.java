package com.mulook.pos.dto;

import com.mulook.pos.entity.ItemType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    @NotEmpty(message = "픔목명은 필수 입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다")
    private int price;

    private String imgUrl;

    @NotNull
    private ItemType itemType;

    public ItemDto() {
    }
}