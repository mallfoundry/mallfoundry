package com.mallfoundry.spring.boot.autoconfigure.cart;

import com.mallfoundry.cart.CartRepository;
import com.mallfoundry.cart.CartService;
import com.mallfoundry.cart.DefaultCartService;
import com.mallfoundry.store.product.ProductService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@EnableConfigurationProperties(CartProperties.class)
public class CartAutoConfiguration {

    @Bean
    @ConditionalOnClass(DefaultCartService.class)
    public CartService cartService(CartProperties properties,
                                   ProductService productService,
                                   CartRepository cartRepository) {
        var service = new DefaultCartService(productService, cartRepository);
        if (Objects.nonNull(properties.getScope())) {
            service.setCartScope(properties.getScope());
        }
        return service;
    }
}
