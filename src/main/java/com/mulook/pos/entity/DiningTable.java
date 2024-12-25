package com.mulook.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class DiningTable {

    @Id @GeneratedValue
    @Column(name = "dining_id")
    private Long id;

    @Column(unique = true)
    private int name;

    @OneToMany(mappedBy = "diningTable", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public DiningTable() {
    }

    public DiningTable(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DiningTable{" +
            "id=" + id +
            ", name=" + name +
            ", orders=" + orders +
            '}';
    }
}