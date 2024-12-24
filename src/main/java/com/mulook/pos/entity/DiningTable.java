package com.mulook.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DiningTable {

    @Id @GeneratedValue
    @Column(name = "dining_id")
    private Long id;

}