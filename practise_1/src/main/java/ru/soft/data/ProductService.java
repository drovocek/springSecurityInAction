package ru.soft.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.soft.data.model.Product;
import ru.soft.data.repo.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
}