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

package org.mallfoundry.store;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mallfoundry.StaticServer;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTests {

    private static final String PRODUCT_VARIANT_ID_VALUE_NAME = "store.product.variant.id";

    private static final String PRODUCT_OPTION_ID_VALUE_NAME = "store.product.option.id";

    private static final String PRODUCT_OPTION_VALUE_ID_VALUE_NAME = "store.product.option.value.id";

    @Autowired
    private ProductService productService;

    @Test
    @Rollback(false)
    @Transactional
    public void testGetProduct() {

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
        productService.addProduct(this.newProduct2_1());
        productService.addProduct(this.newProduct3());
        productService.addProduct(this.newProduct4());
        productService.addProduct(this.newProduct5());
    }

    private String resolveImageUrl(String id) {
        return StaticServer.BASE_URL + "/images/" + id;
    }

    private Product newProduct1() {
        double price = 0.01;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("huawei")
                .name("华为 HUAWEI Mate 30 Pro 5G 麒麟990 OLED环幕屏双4000万徕卡电影四摄手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                .imageUrl(resolveImageUrl("e070a0bc693efc85.jpg"))
                .imageUrl(resolveImageUrl("cd96fb7761beeb9e.jpg"))
                .imageUrl(resolveImageUrl("c78c80a4116ee57d.jpg"))
                .imageUrl(resolveImageUrl("777b12adea1822f6.jpg"))
                //
                .option(product1 -> product1.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("翡冷翠").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("丹霞橙").build())
                        .build())
                .option(product1 -> product1.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "翡冷翠").orElseThrow(),
                                product1.selectOption("版本", "8GB 128GB").orElseThrow()))
                        .position(0).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "翡冷翠").orElseThrow(),
                                product1.selectOption("版本", "12GB 256GB").orElseThrow()))
                        .position(0).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("c78c80a4116ee57d.jpg"), resolveImageUrl("777b12adea1822f6.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "丹霞橙").orElseThrow(),
                                product1.selectOption("版本", "12GB 256GB").orElseThrow()))
                        .position(1).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("c78c80a4116ee57d.jpg"), resolveImageUrl("777b12adea1822f6.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "丹霞橙").orElseThrow(),
                                product1.selectOption("版本", "12GB 256GB").orElseThrow()))
                        .position(1).build())
                //attributes
                .attribute(product1 -> product1.createAttribute("产地", "中国"))
                .attribute(product1 -> product1.createAttribute("生成时间", "2018-10-11"))
                .attribute(product1 -> product1.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct2() {
        double price = 0.04;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("mi")
                .name("小米9 Pro 5G 骁龙855Plus 30W无线闪充手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                .collections(Set.of("12236", "12243"))
                .imageUrl(resolveImageUrl("da769739c0a75afb.jpg"))
                .imageUrl(resolveImageUrl("753768612ae90b4e.jpg"))
                .imageUrl(resolveImageUrl("cd769d2bd022de2a.jpg"))
                .imageUrl(resolveImageUrl("62edde5e1ef2fd85.jpg"))
                //
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("梦之白").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("钛银黑").build())
                        .build())

                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.04).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "梦之白").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.06).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "梦之白").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.08).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("cd769d2bd022de2a.jpg"), resolveImageUrl("62edde5e1ef2fd85.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(2).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.1).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("cd769d2bd022de2a.jpg"), resolveImageUrl("62edde5e1ef2fd85.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(3).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct2_1() {
        double price = 0.02;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("mi")
                .name("【向往的生活同款】小米10 双模5G 骁龙865 1亿像素8K电影相机 对称式立体声")
                .type(ProductType.DIGITAL)
                .status(ProductStatus.ACTIVE)
                .collections(Set.of("12230", "12243"))
                .imageUrl(resolveImageUrl("6626003a708ce8ef.jpg"))
                .imageUrl(resolveImageUrl("df797544001e5ba3.jpg"))
                .imageUrl(resolveImageUrl("979bde1d68c3de5b.jpg"))
                .imageUrl(resolveImageUrl("0ad4ba55c20c6903.jpg"))
                .imageUrl(resolveImageUrl("e523be52552921bf.jpg"))
                .imageUrl(resolveImageUrl("6765a52c369741ec.jpg"))
                //
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("钛银黑").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("冰海蓝").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("蜜桃金").build())
                        .build())
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 256GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.1).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.12).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.14).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.16).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(0.18).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "蜜桃金").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "蜜桃金").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "蜜桃金").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct3() {

        double price = 0.01;

        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("oppo")
                .name("OPPO Reno3 Pro 一体化双模5G 视频双防抖 骁龙765G 7.7mm轻薄机身手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                .imageUrl(resolveImageUrl("0ea203c122fb3dae.jpg"))
                .imageUrl(resolveImageUrl("7eac762ed4bcb66f.jpg"))
                .imageUrl(resolveImageUrl("b72e95f6953e2f3c.jpg"))
                .imageUrl(resolveImageUrl("c682d018d572c792.jpg"))
                .imageUrl(resolveImageUrl("8bf9144ad8c26840.jpg"))
                .imageUrl(resolveImageUrl("6f728a6562668d8f.jpg"))
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("雾月白").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("日出印象").build())
                        .build())

                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 256GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "日出印象").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("8bf9144ad8c26840.jpg"), resolveImageUrl("6f728a6562668d8f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "日出印象").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("8bf9144ad8c26840.jpg"), resolveImageUrl("6f728a6562668d8f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "日出印象").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct4() {
        double price = 0.01;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("vivo")
                .name("vivo NEX3 无界瀑布屏 高通骁龙855Plus 6400万三摄5G全网通手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                //
                .imageUrl(resolveImageUrl("3c5048ac3b93dcca.png"))
                .imageUrl(resolveImageUrl("3c5048ac3b93dccc.jpg"))
                .imageUrl(resolveImageUrl("ec48ee3a1e78a5ce.jpg"))
                .imageUrl(resolveImageUrl("178e05db88b4477e.jpg"))
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("深空流光").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("液态天河").build())
                        .build())

                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 256GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "深空流光").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "深空流光").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.png"), resolveImageUrl("178e05db88b4477e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "液态天河").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.png"), resolveImageUrl("178e05db88b4477e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "液态天河").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct5() {
        double price = 0.01;

        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("one plus")
                .name("一加 OnePlus 7 Pro 2K+90Hz 流体屏 骁龙855旗舰 4800万超广角三摄手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                //
                .imageUrl(resolveImageUrl("47fdb0779e7dad8a.jpg"))
                .imageUrl(resolveImageUrl("5cdd0ce2N5852750d.jpg"))
                .imageUrl(resolveImageUrl("0d0601b02dbb38e9.jpg"))
                .imageUrl(resolveImageUrl("5cdd0d93N3d7e0776.jpg"))
                .imageUrl(resolveImageUrl("a95c82b7c278fe1a.jpg"))
                .imageUrl(resolveImageUrl("3e4ee6b91564649d.jpg"))
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("曜岩灰").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("星雾蓝").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("皓月金").build())
                        .build())

                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("6GB 128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("8GB 256GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("12GB 256GB").build())
                        .build())
                //

                .variant(product -> product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME))
                        .toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                        .optionSelections(List.of(
                                product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                product.selectOption("版本", "6GB 128GB").orElseThrow()))
                        .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "6GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "皓月金").orElseThrow(),
                                        product.selectOption("版本", "6GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "皓月金").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "皓月金").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

}
