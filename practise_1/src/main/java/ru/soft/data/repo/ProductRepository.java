package ru.soft.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.soft.data.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
} 