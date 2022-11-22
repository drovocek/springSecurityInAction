package ru.soft.data.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUTHORITY", schema = "SPRING")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JoinColumn(name = "app_user_id")
    @ManyToOne
    private User user;
}