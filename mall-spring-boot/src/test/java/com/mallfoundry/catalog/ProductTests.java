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

package com.mallfoundry.catalog;


import com.mallfoundry.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTests {


    @Autowired
    private ProductService productService;

    @Test
    public void testGetProduct() {

        Product product = productService.getProduct("10000000000010");

        System.out.println(product);
        String productJsonString = JsonUtils.stringify(product);
        System.out.println(productJsonString);
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProduct() {
        Product product1 = this.newProduct(
                "华为 HUAWEI P30 Pro 超感光徕卡四摄10倍混合变焦麒麟980芯片屏内指纹 8GB+128GB极光色全网通版双4G手机",
                4988,
                "http://192.168.0.102:8077/static/images/800_800_1555464685019mp.png");
        productService.createProduct(product1);

        Product product2 = this.newProduct(
                "小米9 4800万超广角三摄 6GB+128GB全息幻彩蓝 骁龙855 全网通4G 双卡双待 水滴全面屏拍照智能游戏手机",
                2599,
                "http://192.168.0.102:8077/static/images/10001477_1568011758561_750x750.jpg");
        productService.createProduct(product2);

        Product product3 = this.newProduct(
                "OPPO Reno2 4800万变焦四摄 视频防抖 6.5英寸阳光护眼全面屏 8GB+128GB 薄雾粉 拍照游戏智能手机",
                2999,
                "http://192.168.0.102:8077/static/images/201908300308085d68d044c3bcc.png");
        productService.createProduct(product3);

        Product product4 = this.newProduct(
                "vivo NEX3 无界瀑布屏 高通骁龙855Plus 6400万三摄5G全网通手机 深空流光 8GB 256GB",
                5698,
                "http://192.168.0.102:8077/static/images/10001477_1568011758562_750x750.png");
        productService.createProduct(product4);

        Product product5 = this.newProduct(
                "一加 OnePlus 7 Pro 2K+90Hz 流体屏 骁龙855旗舰 4800万超广角三摄 8GB+256GB 星雾蓝 全面屏拍照游戏手机",
                4299,
                "http://192.168.0.102:8077/static/images/eb48f0aa21853fe648a29523231b736d_840_840.png");
        productService.createProduct(product5);
    }

    private Product newProduct(String name, double retailPrice, String imageUrl) {
        Product product = new Product();
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("泳衣女");
        product.setDescription(name);
        product.setImages(List.of(new ProductImage(imageUrl, (short) 0),
                new ProductImage(imageUrl, (short) 1),
                new ProductImage(imageUrl, (short) 2)));
        product.setSkus(List.of(
                new ProductSKU.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100).specs(List.of(1, 5)).index(0).build(),
                new ProductSKU.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100).specs(List.of(2, 5)).index(1).build(),
                new ProductSKU.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100).specs(List.of(3, 5)).index(2).build()));

        product.setAttributes(List.of(
                new ProductAttribute("产地", "中国", (short) 1),
                new ProductAttribute("生成时间", "2018-10-11", (short) 2),
                new ProductAttribute("颜色", "黑", (short) 3)));

        product.setSpecs(List.of(
                new ProductSpecification((short) 0, "颜色", List.of(
                        new ProductSpecItem(0, "黑色",
                                List.of(new ProductImage(imageUrl, (short) 0),
                                        new ProductImage(imageUrl, (short) 1),
                                        new ProductImage(imageUrl, (short) 2))),
                        new ProductSpecItem(1, "红色",
                                List.of(new ProductImage(imageUrl, (short) 0),
                                        new ProductImage(imageUrl, (short) 1),
                                        new ProductImage(imageUrl, (short) 2))),
                        new ProductSpecItem(2, "蓝色",
                                List.of(new ProductImage(imageUrl, (short) 0),
                                        new ProductImage(imageUrl, (short) 1),
                                        new ProductImage(imageUrl, (short) 2))),
                        new ProductSpecItem(3, "褐色",
                                List.of(new ProductImage(imageUrl, (short) 0),
                                        new ProductImage(imageUrl, (short) 1),
                                        new ProductImage(imageUrl, (short) 2)))
                )),
                new ProductSpecification((short) 1, "型号", List.of(
                        new ProductSpecItem(4, "32G"),
                        new ProductSpecItem(5, "64G"),
                        new ProductSpecItem(6, "128G"),
                        new ProductSpecItem(7, "256G")
                ))
        ));

        return product;
    }
}
