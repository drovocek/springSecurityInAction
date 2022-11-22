package ru.soft.data.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PRODUCT", schema = "SPRING")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
