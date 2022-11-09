package com.example.springjpaground.onetoone.application;

import com.example.springjpaground.onetoone.domain.Product;
import com.example.springjpaground.onetoone.domain.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;



    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
