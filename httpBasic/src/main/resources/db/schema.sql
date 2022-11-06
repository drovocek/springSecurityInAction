CREATE SCHEMA IF NOT EXISTS spring;

CREATE TABLE IF NOT EXISTS spring.users (
     id identity primary key,
     username VARCHAR(45) NOT NULL,
     password VARCHAR(45) NOT NULL,
     enabled INT NOT NULL
);

CREATE TABLE IF NOT EXISTS spring.authorities (
     id identity primary key,
     username VARCHAR(45) NOT NULL,
     authority VARCHAR(45) NOT NULL
);
