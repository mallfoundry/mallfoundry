package com.github.shop;

import com.github.shop.product.Product;
import com.github.shop.product.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallProductSpringBootApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void contextLoads() {

        Product product = productRepository.find(1);
//        product.setFreeShipping();
        System.out.println(product);
    }

}
