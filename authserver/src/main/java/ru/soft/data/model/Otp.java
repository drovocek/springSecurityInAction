package ru.soft.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "OTP")
public class Otp {

    @Id
    private String username;

    private String code;
}
