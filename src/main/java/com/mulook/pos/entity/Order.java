package com.mulook.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "dining_id")
   private DiningTable diningTable;

   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime created;


    // ==  연관 관계 메서드 DiningTable 연결 == //
    public void addDining(DiningTable diningTable) {
        this.diningTable = diningTable;
        this.created = LocalDateTime.now();
        if (!diningTable.getOrders().contains(this)) {
            diningTable.getOrders().add(this);
        }
    }

    // ==  연관 관계 메서드 orderItem 연결 == //
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.linkOrder(this);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderItems=" + orderItems +
            ", created=" + created +
            '}';
    }
}