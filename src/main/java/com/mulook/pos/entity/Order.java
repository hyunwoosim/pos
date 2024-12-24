package com.mulook.pos.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;



   @ManyToOne
   @JoinColumn(name = "tb_id")
   private DiningTable diningTable;

   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime created;


    // ==  연관 관계 메서드 member 연결 == //
    public void addOrder(DiningTable diningTable, LocalDateTime created) {
        this.diningTable = diningTable;
        this.created = created;
    }

    // ==  연관 관계 메서드 orderItem 연결 == //
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.linkOrder(this);
    }
}