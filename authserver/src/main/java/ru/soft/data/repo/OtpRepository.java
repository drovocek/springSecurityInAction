package ru.soft.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.soft.data.model.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {
    
    Optional<Otp> findOtpByUsername(String username);
}