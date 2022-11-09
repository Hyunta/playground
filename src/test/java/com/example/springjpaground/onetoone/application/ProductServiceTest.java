package com.example.springjpaground.onetoone.application;

import com.example.springjpaground.onetoone.domain.Product;
import com.example.springjpaground.onetoone.domain.ProductPrice;
import com.example.springjpaground.onetoone.domain.ProductPriceRepository;
import com.example.springjpaground.onetoone.domain.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProductServiceTest {

    //OneToOne일 경우 연관관계의 주인이 아닌 쪽은 Lazy가 걸린다.

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;


    @Test
    void oneToOne_Lazy() {
        Product product = new Product(null, new ProductPrice(null, null, 1000));
        productRepository.save(product);

        Product product1 = productRepository.findById(product.getId()).get();
        System.out.println("product1 = " + product1);
    }

    @Test
    void oneToOne_Lazy2() {
        ProductPrice productPrice = new ProductPrice(null, null, 1000);
        Product product = new Product(null, productPrice);
        productRepository.save(product);

        ProductPrice productPrice1 = productPriceRepository.findById(productPrice.getId()).get();
        System.out.println("productPrice1 = " + productPrice1);
        List<ProductPrice> all = productPriceRepository.findAll();
        System.out.println("all = " + all);
    }
}
