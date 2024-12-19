package com.mulook.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public void createItem(String name, int price, String imgUrl, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.itemType = itemType;
    }
}