package ru.soft.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.soft.data.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String u);
}