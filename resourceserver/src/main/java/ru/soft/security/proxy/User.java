package ru.soft.security.proxy;

import lombok.Value;

@Value
public class User {
    String username;
    String password;
    String code;
}
