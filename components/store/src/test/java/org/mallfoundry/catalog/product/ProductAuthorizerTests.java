package org.mallfoundry.catalog.product;

import org.junit.jupiter.api.Test;
import org.mallfoundry.catalog.product.plugins.ProductAuthorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class ProductAuthorizerTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAuthorizer productAuthorizer;

    @WithMockUser(username = "MockUser", password = "N/A", authorities = {"ADMIN"})
    @Test
    public void testPreAddProduct() {
        var product = this.productService.createProduct("1").toBuilder().storeId("mi").build();
        this.productAuthorizer.preAddProduct(product);
    }
}
