package com.mallfoundry.spring.boot.autoconfigure.cart;

import com.mallfoundry.cart.CartScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mall.cart")
public class CartProperties {
    private CartScope scope = CartScope.GLOBAL;
}
