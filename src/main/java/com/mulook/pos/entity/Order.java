package com.mulook.pos.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int count;
    private int totalPrice;
    private LocalDateTime created;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public void orderAdd(Long id, int count, int totalPrice, LocalDateTime created, List<Item> items) {
        this.id = id;
        this.count = count;
        this.totalPrice = totalPrice;
        this.created = created;
        this.items = items;
    }
}