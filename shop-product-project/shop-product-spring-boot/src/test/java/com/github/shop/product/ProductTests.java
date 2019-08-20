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

package com.github.shop.product;


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
    private ProductRepository productRepository;

    @Test
    public void testGetProduct() throws Exception {

        Product product = productRepository.find(10000000000010L);

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
        product.setTitle("泳衣女遮肚显瘦保守连体裙性感2019新款爆款大码胖mm学生少女仙女");
        product.setShortName("泳衣女");
        product.setDescription("泳衣女遮肚显瘦保守连体裙性感2019新款爆款大码胖mm学生少女仙女");

        product.setItems(List.of(
                new ProductItem(BigDecimal.valueOf(100), BigDecimal.valueOf(200), 100, List.of(1, 5),
                        List.of(
                                new ProductImage("/a.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/b.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/c.png", (short) 100, (short) 200, (short) 1)
                        ),
                        List.of(
                                new ProductVideo("/a.mp4", (short) 200, (short) 1),
                                new ProductVideo("/b.mp4", (short) 200, (short) 2),
                                new ProductVideo("/c.mp4", (short) 200, (short) 3)
                        ), (short) 1),
                new ProductItem(BigDecimal.valueOf(100), BigDecimal.valueOf(180), 99, List.of(2, 6),
                        List.of(
                                new ProductImage("/a1.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/b1.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/c1.png", (short) 100, (short) 200, (short) 1)
                        ),
                        List.of(
                                new ProductVideo("/a1.mp4", (short) 200, (short) 1),
                                new ProductVideo("/b1.mp4", (short) 200, (short) 2),
                                new ProductVideo("/c1.mp4", (short) 200, (short) 3)
                        ), (short) 2),
                new ProductItem(BigDecimal.valueOf(100), BigDecimal.valueOf(300), 80, List.of(3, 8),
                        List.of(
                                new ProductImage("/a2.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/b3.png", (short) 100, (short) 200, (short) 1),
                                new ProductImage("/c4.png", (short) 100, (short) 200, (short) 1)
                        ),
                        List.of(
                                new ProductVideo("/a1.mp4", (short) 200, (short) 1),
                                new ProductVideo("/b2.mp4", (short) 200, (short) 2),
                                new ProductVideo("/c3.mp4", (short) 200, (short) 3)
                        ), (short) 3)));

        product.setAttributes(List.of(
                new ProductAttribute("产地", "中国", true, (short) 1),
                new ProductAttribute("生成时间", "2018-10-11", true, (short) 2),
                new ProductAttribute("颜色", "黑", true, (short) 3)));

        product.setSpecs(List.of(
                new ProductSpecification("颜色", "颜色--111", List.of(
                        new ProductSpecificationItem(1, "黑色", null),
                        new ProductSpecificationItem(2, "红色", null),
                        new ProductSpecificationItem(3, "蓝色", null),
                        new ProductSpecificationItem(4, "褐色", null)
                ), (short) 1),
                new ProductSpecification("型号", "型号--111", List.of(
                        new ProductSpecificationItem(5, "32G", null),
                        new ProductSpecificationItem(6, "64G", null),
                        new ProductSpecificationItem(7, "128G", null),
                        new ProductSpecificationItem(8, "256G", null)
                ), (short) 1)
        ));
        productRepository.create(product);
        System.out.println(product);
    }
}
