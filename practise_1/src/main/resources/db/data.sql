INSERT INTO SPRING.APP_USER (id, username, password, algorithm)
VALUES ('1', 'john','$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG','BCRYPT');

INSERT INTO SPRING.AUTHORITY (id, name, app_user_id)
VALUES ('1', 'READ', '1');

INSERT INTO SPRING.AUTHORITY (id, name, app_user_id)
VALUES ('2', 'WRITE', '1');

INSERT INTO SPRING.PRODUCT (id, name, price, currency)
VALUES ('1', 'Chocolate', '10', 'USD');