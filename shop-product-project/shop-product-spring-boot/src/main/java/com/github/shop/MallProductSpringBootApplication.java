package com.github.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MallProductSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductSpringBootApplication.class, args);
    }
}
