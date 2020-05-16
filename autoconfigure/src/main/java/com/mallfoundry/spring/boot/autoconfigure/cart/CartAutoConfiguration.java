package com.mallfoundry.spring.boot.autoconfigure.cart;

import com.mallfoundry.cart.CartTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CartProperties.class)
public class CartAutoConfiguration {

    @Bean
    @ConditionalOnClass(CartTokenService.class)
    @ConditionalOnProperty(prefix = "mall.cart", name = "scope")
    public CartTokenService cartTokenService(CartProperties properties) {
        return new CartTokenService(properties.getScope());
    }
}
