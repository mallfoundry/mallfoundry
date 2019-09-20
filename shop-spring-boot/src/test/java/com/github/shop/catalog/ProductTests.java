/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shop.catalog;


import com.github.shop.util.JsonUtils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTests {


    @Autowired
    private ProductService productService;

    @Test
    public void testGetProduct() {

        Product product = productService.getProduct(10000000000010L);

        System.out.println(product);
        String productJsonString = JsonUtils.stringify(product);
        System.out.println(productJsonString);
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProduct() {
        Product product = new Product();
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName("泳衣女遮肚显瘦保守连体裙性感2019新款爆款大码胖mm学生少女仙女");
        product.setShortName("泳衣女");
        product.setDescription("泳衣女遮肚显瘦保守连体裙性感2019新款爆款大码胖mm学生少女仙女");

        product.setSkus(List.of(
                new ProductSku.Builder().marketPrice(100).retailPrice(100).stockQuantity(100).specs(List.of(1, 5)).index(1).build(),
                new ProductSku.Builder().marketPrice(200).retailPrice(200).stockQuantity(100).specs(List.of(2, 5)).index(2).build(),
                new ProductSku.Builder().marketPrice(300).retailPrice(300).stockQuantity(100).specs(List.of(3, 5)).index(3).build()));

        product.setAttributes(List.of(
                new ProductAttribute("产地", "中国", (short) 1),
                new ProductAttribute("生成时间", "2018-10-11", (short) 2),
                new ProductAttribute("颜色", "黑", (short) 3)));

        product.setSpecs(List.of(
                new ProductSpecification((short) 1, "颜色", List.of(
                        new ProductSpecItem(1, "黑色",
                                List.of(new ProductImage("http://localhost/a.png", (short) 1),
                                        new ProductImage("http://localhost/b.png", (short) 2),
                                        new ProductImage("http://localhost/c.png", (short) 3))),
                        new ProductSpecItem(2, "红色",
                                List.of(new ProductImage("http://localhost/a.png", (short) 1),
                                        new ProductImage("http://localhost/b.png", (short) 2),
                                        new ProductImage("http://localhost/c.png", (short) 3))),
                        new ProductSpecItem(3, "蓝色",
                                List.of(new ProductImage("http://localhost/a.png", (short) 1),
                                        new ProductImage("http://localhost/b.png", (short) 2),
                                        new ProductImage("http://localhost/c.png", (short) 3))),
                        new ProductSpecItem(4, "褐色",
                                List.of(new ProductImage("http://localhost/a.png", (short) 1),
                                        new ProductImage("http://localhost/b.png", (short) 2),
                                        new ProductImage("http://localhost/c.png", (short) 3)))
                )),
                new ProductSpecification((short) 1, "型号", List.of(
                        new ProductSpecItem(5, "32G"),
                        new ProductSpecItem(6, "64G"),
                        new ProductSpecItem(7, "128G"),
                        new ProductSpecItem(8, "256G")
                ))
        ));
        productService.createProduct(product);
        System.out.println(product);
    }
}
