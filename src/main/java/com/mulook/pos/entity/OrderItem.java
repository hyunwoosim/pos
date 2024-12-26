package com.mulook.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int totalPrice;

    private int count;

    // == 연관 관계 메서드 ==//
    public void linkOrder(Order order) {
        this.order = order;
    }

    //== 비즈니스 로직 ==//
    public void  addOrderItem(Item item, int totalPrice, int count) {
        this.item = item;
        this.totalPrice = totalPrice;
        this.count = count;

    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + id +
            ", item=" + item +
            ", totalPrice=" + totalPrice +
            ", count=" + count +
            '}';
    }
}