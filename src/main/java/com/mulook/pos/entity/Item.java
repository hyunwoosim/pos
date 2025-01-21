package com.mulook.pos.entity;

import com.mulook.pos.entity.Enum.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Item {


    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;



    public void createItem(String name, int price, String imgUrl, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", imgUrl='" + imgUrl + '\'' +
            ", itemType=" + itemType +
            '}';
    }
}