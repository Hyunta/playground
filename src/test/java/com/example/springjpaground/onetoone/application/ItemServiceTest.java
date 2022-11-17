package com.example.springjpaground.onetoone.application;

import com.example.springjpaground.onetoone.domain.Product;
import com.example.springjpaground.onetoone.domain.ProductPrice;
import com.example.springjpaground.onetoone.domain.ProductPriceRepository;
import com.example.springjpaground.onetoone.domain.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ItemServiceTest {

    //OneToOne일 경우 연관관계의 주인이 아닌 쪽은 Lazy가 걸린다.

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;


    @Test
    @DisplayName("연관관계의 주인을 조회하면 Lazy가 작동한다.")
    void oneToOne_Lazy() {
        Product product = new Product(null, new ProductPrice(null, null, 1000));
        productRepository.save(product);

        Product product1 = productRepository.findById(product.getId()).get();
        List<Product> all = productRepository.findAll();
        System.out.println("product1 = " + product1);
        System.out.println("all = " + all);
    }

    @Test
    @DisplayName("연관관계의 주인이 아닌 쪽을 조회하면 Lazy가 작동하지 않는다.")
    void oneToOne_Lazy2() {
        ProductPrice productPrice = new ProductPrice(null, null, 1000);
        Product product = new Product(null, productPrice);
        productRepository.save(product);

        ProductPrice productPrice1 = productPriceRepository.findById(productPrice.getId()).get();
        List<ProductPrice> all = productPriceRepository.findAll();

        System.out.println("productPrice1 = " + productPrice1);
        System.out.println("all = " + all);
    }
}
