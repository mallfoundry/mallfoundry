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

package com.mallfoundry.store;


import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductAttribute;
import com.mallfoundry.store.product.ProductImage;
import com.mallfoundry.store.product.ProductService;
import com.mallfoundry.store.product.ProductVariant;
import com.mallfoundry.store.product.ProductVideo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTests {


    @Autowired
    private ProductService productService;

    @Test
    @Rollback(false)
    @Transactional
    public void testGetProduct() {

        Optional<Product> productOptional = productService.getProduct(153L);

        if (productOptional.isPresent()) {
            System.out.println();
            Product product = productOptional.get();
            List<ProductVideo> videos = product.getVideos();
            System.out.println(videos);
        }

    }

    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProduct() {
        this.newProduct4();
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProducts() {

        productService.addProduct(this.newProduct1());
        productService.addProduct(this.newProduct2());
        productService.addProduct(this.newProduct3());
        productService.addProduct(this.newProduct4());
        productService.addProduct(this.newProduct5());
    }

    private String getImageUrl(String id) {
        return "http://192.168.0.102:8077/static/images/" + id;
    }

    private Product newProduct1() {
        Product product = new Product();
        String name = "华为 HUAWEI Mate 30 Pro 5G 麒麟990 OLED环幕屏双4000万徕卡电影四摄手机";
        double retailPrice = 6899.00;
        product.setStoreId(new StoreId("huawei"));
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("手机");
        product.setDescription(name);

        product.addImage(new ProductImage("huawei_1", this.getImageUrl("e070a0bc693efc85.jpg"), 0));
        product.addImage(new ProductImage("huawei_2", this.getImageUrl("cd96fb7761beeb9e.jpg"), 1));
        product.addImage(new ProductImage("huawei_3", this.getImageUrl("c78c80a4116ee57d.jpg"), 2));
        product.addImage(new ProductImage("huawei_4", this.getImageUrl("777b12adea1822f6.jpg"), 3));

        product.createOption("颜色").addSimpleValues("翡冷翠", "丹霞橙");
        product.createOption("版本").addSimpleValues("8GB 128GB", "12GB 256GB");

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("huawei_1", "huawei_2")).options(List.of("翡冷翠", "8GB 128GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("huawei_1", "huawei_2")).options(List.of("翡冷翠", "12GB 256GB")).position(0).build());

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("huawei_3", "huawei_4")).options(List.of("丹霞橙", "8GB 128GB")).position(1).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("huawei_3", "huawei_4")).options(List.of("丹霞橙", "12GB 256GB")).position(1).build());

        product.addAttribute(new ProductAttribute("产地", "中国", 1));
        product.addAttribute(new ProductAttribute("生成时间", "2018-10-11", 2));
        product.addAttribute(new ProductAttribute("颜色", "黑", 3));
        return product;
    }

    private Product newProduct2() {
        Product product = this.productService.createProduct();
        String name = "小米9 Pro 5G 骁龙855Plus 30W无线闪充手机";
        double retailPrice = 3799.00;
        product.setStoreId(new StoreId("mi"));
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("手机");
        product.setDescription(name);

        product.addImage(new ProductImage("mi_1", this.getImageUrl("da769739c0a75afb.jpg"), 0));
        product.addImage(new ProductImage("mi_2", this.getImageUrl("753768612ae90b4e.jpg"), 1));
        product.addImage(new ProductImage("mi_3", this.getImageUrl("cd769d2bd022de2a.jpg"), 2));
        product.addImage(new ProductImage("mi_4", this.getImageUrl("62edde5e1ef2fd85.jpg"), 3));

        product.createOption("颜色").addSimpleValues("梦之白", "钛银黑");
        product.createOption("版本").addSimpleValues("8GB 128GB", "12GB 256GB");

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("mi_1", "mi_2")).options(List.of("梦之白", "8GB 128GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("mi_1", "mi_2")).options(List.of("梦之白", "12GB 256GB")).position(1).build());

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("mi_3", "mi_4")).options(List.of("钛银黑", "8GB 128GB")).position(2).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("mi_3", "mi_4")).options(List.of("钛银黑", "12GB 256GB")).position(3).build());

        product.addAttribute(new ProductAttribute("产地", "中国", 1));
        product.addAttribute(new ProductAttribute("生成时间", "2018-10-11", 2));
        product.addAttribute(new ProductAttribute("颜色", "黑", 3));
        return product;
    }

    private Product newProduct3() {
        Product product = this.productService.createProduct();
        String name = "OPPO Reno3 Pro 一体化双模5G 视频双防抖 骁龙765G 7.7mm轻薄机身手机";
        double retailPrice = 9999;
        product.setStoreId(new StoreId("oppo"));
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("手机");
        product.setDescription(name);

        product.addImage(new ProductImage("oppo_1", this.getImageUrl("0ea203c122fb3dae.jpg"), 0));
        product.addImage(new ProductImage("oppo_2", this.getImageUrl("7eac762ed4bcb66f.jpg"), 1));
        product.addImage(new ProductImage("oppo_3", this.getImageUrl("b72e95f6953e2f3c.jpg"), 2));
        product.addImage(new ProductImage("oppo_4", this.getImageUrl("c682d018d572c792.jpg"), 3));
        product.addImage(new ProductImage("oppo_7", this.getImageUrl("8bf9144ad8c26840.jpg"), 2));
        product.addImage(new ProductImage("oppo_8", this.getImageUrl("6f728a6562668d8f.jpg"), 3));

        product.createOption("颜色").addSimpleValues("雾月白", "日出印象", "蓝色星夜");
        product.createOption("版本").addSimpleValues("8GB 256GB", "12GB 256GB");

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_1", "oppo_2")).options(List.of("雾月白", "8GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_1", "oppo_2")).options(List.of("雾月白", "12GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_3", "oppo_4")).options(List.of("日出印象", "8GB 256GB")).position(1).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_3", "oppo_4")).options(List.of("日出印象", "12GB 256GB")).position(1).build());

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_7", "oppo_8")).options(List.of("蓝色星夜", "8GB 256GB")).position(1).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("oppo_7", "oppo_8")).options(List.of("蓝色星夜", "12GB 256GB")).position(1).build());

        product.addAttribute(new ProductAttribute("产地", "中国", 1));
        product.addAttribute(new ProductAttribute("生成时间", "2018-10-11", 2));
        product.addAttribute(new ProductAttribute("颜色", "黑", 3));
        return product;
    }

    private Product newProduct4() {
        Product product = this.productService.createProduct();
        String name = "vivo NEX3 无界瀑布屏 高通骁龙855Plus 6400万三摄5G全网通手机";
        double retailPrice = 5698;
        product.setStoreId(new StoreId("vivo"));
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("手机");
        product.setDescription(name);

        product.addImage(new ProductImage("vivo_1", this.getImageUrl("10001477_1568011758562_750x750.png"), 0));
        product.addImage(new ProductImage("vivo_2", this.getImageUrl("3c5048ac3b93dccc.jpg"), 1));
        product.addImage(new ProductImage("vivo_3", this.getImageUrl("ec48ee3a1e78a5ce.jpg"), 2));
        product.addImage(new ProductImage("vivo_4", this.getImageUrl("178e05db88b4477e.jpg"), 3));

        product.createOption("颜色").addSimpleValues("深空流光", "液态天河");
        product.createOption("版本").addSimpleValues("8GB 256GB", "12GB 256GB");

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("vivo_1", "vivo_2")).options(List.of("深空流光", "8GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("vivo_1", "vivo_2")).options(List.of("深空流光", "12GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("vivo_3", "vivo_4")).options(List.of("液态天河", "8GB 256GB")).position(1).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("vivo_3", "vivo_4")).options(List.of("液态天河", "12GB 256GB")).position(1).build());

        product.addAttribute(new ProductAttribute("产地", "中国", 1));
        product.addAttribute(new ProductAttribute("生成时间", "2018-10-11", 2));
        product.addAttribute(new ProductAttribute("颜色", "黑", 3));
        return product;
    }

    private Product newProduct5() {
        String name = "一加 OnePlus 7 Pro 2K+90Hz 流体屏 骁龙855旗舰 4800万超广角三摄手机";
        double retailPrice = 3899;
        Product product = new Product();
        product.setStoreId(new StoreId("one plus"));
        product.setFreeShipping(false);
        product.setShippingMoney(BigDecimal.valueOf(10));
        product.setName(name);
        product.setShortName("手机");
        product.setDescription(name);

        product.addImage(new ProductImage("one_plus_1", this.getImageUrl("47fdb0779e7dad8a.jpg"), 0));
        product.addImage(new ProductImage("one_plus_2", this.getImageUrl("5cdd0ce2N5852750d.jpg"), 1));
        product.addImage(new ProductImage("one_plus_3", this.getImageUrl("0d0601b02dbb38e9.jpg"), 2));
        product.addImage(new ProductImage("one_plus_4", this.getImageUrl("5cdd0d93N3d7e0776.jpg"), 3));
        product.addImage(new ProductImage("one_plus_5", this.getImageUrl("a95c82b7c278fe1a.jpg"), 2));
        product.addImage(new ProductImage("one_plus_6", this.getImageUrl("3e4ee6b91564649d.jpg"), 3));

        product.createOption("颜色").addSimpleValues("曜岩灰", "星雾蓝", "皓月金");
        product.createOption("版本").addSimpleValues("6GB 128GB", "8GB 256GB", "12GB 256GB");

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_1", "one_plus_2")).options(List.of("曜岩灰", "6GB 128GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_1", "one_plus_2")).options(List.of("曜岩灰", "8GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_1", "one_plus_2")).options(List.of("曜岩灰", "12GB 256GB")).position(0).build());

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_3", "one_plus_4")).options(List.of("星雾蓝", "6GB 128GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_3", "one_plus_4")).options(List.of("星雾蓝", "8GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_3", "one_plus_4")).options(List.of("星雾蓝", "12GB 256GB")).position(0).build());

        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_5", "one_plus_6")).options(List.of("皓月金", "6GB 128GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_5", "one_plus_6")).options(List.of("皓月金", "8GB 256GB")).position(0).build());
        product.addVariant(
                new ProductVariant.Builder().marketPrice(retailPrice).retailPrice(retailPrice).stockQuantity(100)
                        .images(List.of("one_plus_5", "one_plus_6")).options(List.of("皓月金", "12GB 256GB")).position(0).build());

        product.addAttribute(new ProductAttribute("产地", "中国", 1));
        product.addAttribute(new ProductAttribute("生成时间", "2018-10-11", 2));
        product.addAttribute(new ProductAttribute("颜色", "黑", 3));
        return product;
    }

}
