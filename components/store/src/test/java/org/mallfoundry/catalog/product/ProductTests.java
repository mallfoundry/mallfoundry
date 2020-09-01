/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.catalog.product;


import org.junit.jupiter.api.Test;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.test.StandaloneTest;
import org.mallfoundry.test.StaticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@StandaloneTest
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
        System.out.println("");
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProduct() {
        this.newProduct4();
    }

    @WithUserDetails("mf_1")
    @Test
    @Rollback(false)
    @Transactional
    public void testSaveProducts() throws InterruptedException {
        productService.addProduct(this.newProduct1());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct2());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct2_1());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct2_redmi_k30());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct2_redmi9());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct3());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct4());
        Thread.sleep(1000 * 2);
        productService.addProduct(this.newProduct5());
    }

    private String resolveImageUrl(String id) {
        return StaticUtils.resolve("/images/" + id);
    }

    private Product newProduct1() {
        double price = 0.01;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("huawei")
                .name("华为 HUAWEI Mate 30 Pro 5G 麒麟990 OLED环幕屏双4000万徕卡电影四摄手机")
                .type(ProductType.PHYSICAL)
                .status(ProductStatus.ACTIVE)
                .categories(List.of("1"))
                .brandId("1")
                .imageUrl(resolveImageUrl("e070a0bc693efc85.jpg"))
                .imageUrl(resolveImageUrl("cd96fb7761beeb9e.jpg"))
                .imageUrl(resolveImageUrl("c78c80a4116ee57d.jpg"))
                .imageUrl(resolveImageUrl("777b12adea1822f6.jpg"))
                //
                .freeShipping()
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("90").province("山东省")
                        .cityId("1427").city("济南市")
                        .countyId("11191").county("槐荫区")
                        .build())
                //
                .adjustMonthlySales(10)
                .adjustTotalSales(10)
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
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "翡冷翠").orElseThrow(),
                                product1.selectOption("版本", "8GB 128GB").orElseThrow()))
                        .position(0).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "翡冷翠").orElseThrow(),
                                product1.selectOption("版本", "12GB 256GB").orElseThrow()))
                        .position(0).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("c78c80a4116ee57d.jpg"), resolveImageUrl("777b12adea1822f6.jpg")))
                        .optionSelections(List.of(
                                product1.selectOption("颜色", "丹霞橙").orElseThrow(),
                                product1.selectOption("版本", "12GB 256GB").orElseThrow()))
                        .position(1).build())
                .variant(product1 -> product1.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
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
                .categories(List.of("1"))
                .brandId("1")
                .collections(Set.of("12236", "12243"))
                .imageUrl(resolveImageUrl("da769739c0a75afb.jpg"))
                .imageUrl(resolveImageUrl("753768612ae90b4e.jpg"))
                .imageUrl(resolveImageUrl("cd769d2bd022de2a.jpg"))
                .imageUrl(resolveImageUrl("62edde5e1ef2fd85.jpg"))
                //
                .fixedShippingCost(10.00)
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("82").province("广东省")
                        .cityId("1353").city("深圳市")
                        .countyId("10451").county("罗湖区")
                        .build())
                //
                .adjustMonthlySales(20)
                .adjustTotalSales(20)
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
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.04).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "梦之白").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.06).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "梦之白").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.08).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("cd769d2bd022de2a.jpg"), resolveImageUrl("62edde5e1ef2fd85.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(2).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
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
                .categories(List.of("1"))
                .brandId("1")
                .collections(Set.of("12230", "12243"))
                .imageUrl(resolveImageUrl("6626003a708ce8ef.jpg"))
                .imageUrl(resolveImageUrl("df797544001e5ba3.jpg"))
                .imageUrl(resolveImageUrl("979bde1d68c3de5b.jpg"))
                .imageUrl(resolveImageUrl("0ad4ba55c20c6903.jpg"))
                .imageUrl(resolveImageUrl("e523be52552921bf.jpg"))
                .imageUrl(resolveImageUrl("6765a52c369741ec.jpg"))
                //
                .fixedShippingCost(20.00)
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("82").province("广东省")
                        .cityId("1354").city("珠海市")
                        .countyId("10462").county("金湾区")
                        .build())
                //
                .adjustMonthlySales(30)
                .adjustTotalSales(30)
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
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.12).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.14).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "钛银黑").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.16).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.18).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "冰海蓝").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "蜜桃金").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "蜜桃金").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
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

    private Product newProduct2_redmi_k30() {
        double price = 0.18;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("mi")
                .categories(List.of("1"))
                .brandId("1")
                .name("Redmi K30 Pro 5G先锋 骁龙865旗舰处理器 弹出式超光感全面屏 索尼6400万高清四摄 4700mAh长续航 33W闪充")
                .type(ProductType.DIGITAL)
                .status(ProductStatus.ACTIVE)
                .imageUrl(resolveImageUrl("redmi_k30pro_1_0.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_1_1.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_1_2.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_1_3.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_2_0.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_2_1.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_2_2.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_2_3.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_2_4.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_3_0.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_3_1.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_3_2.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_3_3.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_3_4.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_4_0.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_4_0.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_4_1.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_4_2.jpg"))
                .imageUrl(resolveImageUrl("redmi_k30pro_4_3.jpg"))
                //
                .fixedShippingCost(10.00)
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("82").province("广东省")
                        .cityId("1354").city("珠海市")
                        .countyId("10462").county("金湾区")
                        .build())
                //
                .adjustMonthlySales(30)
                .adjustTotalSales(30)
                //
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("太空灰").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("月慕白").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("天际蓝").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("星环紫").build())
                        .build())
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("标准版8GB+128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("变焦版12GB+256GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_1_0.jpg"), resolveImageUrl("redmi_k30pro_1_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_1_2.jpg"), resolveImageUrl("redmi_k30pro_1_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "太空灰").orElseThrow(),
                                        product.selectOption("版本", "标准版8GB+128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_1_0.jpg"), resolveImageUrl("redmi_k30pro_1_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_1_2.jpg"), resolveImageUrl("redmi_k30pro_1_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "太空灰").orElseThrow(),
                                        product.selectOption("版本", "变焦版12GB+256GB").orElseThrow()))
                                .position(1).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_2_0.jpg"), resolveImageUrl("redmi_k30pro_2_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_2_2.jpg"), resolveImageUrl("redmi_k30pro_2_3.jpg"), resolveImageUrl("redmi_k30pro_2_4.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "月慕白").orElseThrow(),
                                        product.selectOption("版本", "标准版8GB+128GB").orElseThrow()))
                                .position(2).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_2_0.jpg"), resolveImageUrl("redmi_k30pro_2_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_2_2.jpg"), resolveImageUrl("redmi_k30pro_2_3.jpg"), resolveImageUrl("redmi_k30pro_2_4.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "月慕白").orElseThrow(),
                                        product.selectOption("版本", "变焦版12GB+256GB").orElseThrow()))
                                .position(3).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_3_0.jpg"), resolveImageUrl("redmi_k30pro_3_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_3_2.jpg"), resolveImageUrl("redmi_k30pro_3_3.jpg"), resolveImageUrl("redmi_k30pro_3_4.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "天际蓝").orElseThrow(),
                                        product.selectOption("版本", "标准版8GB+128GB").orElseThrow()))
                                .position(4).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_3_0.jpg"), resolveImageUrl("redmi_k30pro_3_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_3_2.jpg"), resolveImageUrl("redmi_k30pro_3_3.jpg"), resolveImageUrl("redmi_k30pro_3_4.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "天际蓝").orElseThrow(),
                                        product.selectOption("版本", "变焦版12GB+256GB").orElseThrow()))
                                .position(5).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_4_0.jpg"), resolveImageUrl("redmi_k30pro_4_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_4_2.jpg"), resolveImageUrl("redmi_k30pro_4_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星环紫").orElseThrow(),
                                        product.selectOption("版本", "标准版8GB+128GB").orElseThrow()))
                                .position(6).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi_k30pro_4_0.jpg"), resolveImageUrl("redmi_k30pro_4_1.jpg"),
                                        resolveImageUrl("redmi_k30pro_4_2.jpg"), resolveImageUrl("redmi_k30pro_4_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星环紫").orElseThrow(),
                                        product.selectOption("版本", "变焦版12GB+256GB").orElseThrow()))
                                .position(7).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("生成时间", "2018-10-11"))
                .attribute(product -> product.createAttribute("颜色", "黑"))
                .create()
                .build();
    }

    private Product newProduct2_redmi9() {
        double price = 0.18;
        return this.productService.createProduct(null)
                .toBuilder()
                .storeId("mi")
                .name("Redmi 9 5020mAh大电量 1080P全高清大屏 大字体大音量大内存 全场景AI四摄 高性能游戏芯 游戏智能手机 小米 红米")
                .type(ProductType.DIGITAL)
                .status(ProductStatus.ACTIVE)
                .categories(List.of("1"))
                .brandId("1")
                .imageUrl(resolveImageUrl("redmi9_1_0.jpg"))
                .imageUrl(resolveImageUrl("redmi9_1_1.jpg"))
                .imageUrl(resolveImageUrl("redmi9_1_2.jpg"))
                .imageUrl(resolveImageUrl("redmi9_1_3.jpg"))
                .imageUrl(resolveImageUrl("redmi9_2_0.jpg"))
                .imageUrl(resolveImageUrl("redmi9_2_1.jpg"))
                .imageUrl(resolveImageUrl("redmi9_2_2.jpg"))
                .imageUrl(resolveImageUrl("redmi9_2_3.jpg"))
                .imageUrl(resolveImageUrl("redmi9_3_0.jpg"))
                .imageUrl(resolveImageUrl("redmi9_3_1.jpg"))
                .imageUrl(resolveImageUrl("redmi9_3_2.jpg"))
                .imageUrl(resolveImageUrl("redmi9_3_3.jpg"))
                .imageUrl(resolveImageUrl("redmi9_4_0.jpg"))
                .imageUrl(resolveImageUrl("redmi9_4_1.jpg"))
                .imageUrl(resolveImageUrl("redmi9_4_2.jpg"))
                .imageUrl(resolveImageUrl("redmi9_4_3.jpg"))
                //
                .fixedShippingCost(10.00)
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("82").province("广东省")
                        .cityId("1354").city("珠海市")
                        .countyId("10462").county("金湾区")
                        .build())
                //
                .adjustMonthlySales(30)
                .adjustTotalSales(30)
                //
                // options
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME)).toBuilder()
                        .name("颜色")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("碳素黑").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("墨黛青").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("霓虹蓝").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("藕荷粉").build())
                        .build())
                .option(product -> product.createOption(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME))
                        .toBuilder()
                        .name("版本")
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("4GB+128GB").build())
                        .value(option -> option.createValue(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME)).toBuilder().label("6GB+128GB").build())
                        .build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_1_0.jpg"), resolveImageUrl("redmi9_1_1.jpg"),
                                        resolveImageUrl("redmi9_1_2.jpg"), resolveImageUrl("redmi9_1_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "碳素黑").orElseThrow(),
                                        product.selectOption("版本", "4GB+128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_1_0.jpg"), resolveImageUrl("redmi9_1_1.jpg"),
                                        resolveImageUrl("redmi9_1_2.jpg"), resolveImageUrl("redmi9_1_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "碳素黑").orElseThrow(),
                                        product.selectOption("版本", "6GB+128GB").orElseThrow()))
                                .position(1).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_2_0.jpg"), resolveImageUrl("redmi9_2_1.jpg"),
                                        resolveImageUrl("redmi9_2_2.jpg"), resolveImageUrl("redmi9_2_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "墨黛青").orElseThrow(),
                                        product.selectOption("版本", "4GB+128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_2_0.jpg"), resolveImageUrl("redmi9_2_1.jpg"),
                                        resolveImageUrl("redmi9_2_2.jpg"), resolveImageUrl("redmi9_2_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "墨黛青").orElseThrow(),
                                        product.selectOption("版本", "6GB+128GB").orElseThrow()))
                                .position(1).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_3_0.jpg"), resolveImageUrl("redmi9_3_1.jpg"),
                                        resolveImageUrl("redmi9_3_2.jpg"), resolveImageUrl("redmi9_3_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "霓虹蓝").orElseThrow(),
                                        product.selectOption("版本", "4GB+128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_3_0.jpg"), resolveImageUrl("redmi9_3_1.jpg"),
                                        resolveImageUrl("redmi9_3_2.jpg"), resolveImageUrl("redmi9_3_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "霓虹蓝").orElseThrow(),
                                        product.selectOption("版本", "6GB+128GB").orElseThrow()))
                                .position(1).build())
                //
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.1).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_4_0.jpg"), resolveImageUrl("redmi9_4_1.jpg"),
                                        resolveImageUrl("redmi9_4_2.jpg"), resolveImageUrl("redmi9_4_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "藕荷粉").orElseThrow(),
                                        product.selectOption("版本", "4GB+128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(0.3).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("redmi9_4_0.jpg"), resolveImageUrl("redmi9_4_1.jpg"),
                                        resolveImageUrl("redmi9_4_2.jpg"), resolveImageUrl("redmi9_4_3.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "藕荷粉").orElseThrow(),
                                        product.selectOption("版本", "6GB+128GB").orElseThrow()))
                                .position(1).build())
                //attributes
                .attribute(product -> product.createAttribute("产地", "中国"))
                .attribute(product -> product.createAttribute("上市年份", "2020年"))
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
                .categories(List.of("1"))
                .brandId("1")
                .imageUrl(resolveImageUrl("0ea203c122fb3dae.jpg"))
                .imageUrl(resolveImageUrl("7eac762ed4bcb66f.jpg"))
                .imageUrl(resolveImageUrl("b72e95f6953e2f3c.jpg"))
                .imageUrl(resolveImageUrl("c682d018d572c792.jpg"))
                .imageUrl(resolveImageUrl("8bf9144ad8c26840.jpg"))
                .imageUrl(resolveImageUrl("6f728a6562668d8f.jpg"))
                //
                .freeShipping()
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("80").province("湖南省")
                        .cityId("1330").city("湘潭市")
                        .countyId("10241").county("雨湖区")
                        .build())
                //
                .adjustMonthlySales(40)
                .adjustTotalSales(40)
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
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "雾月白").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "日出印象").orElseThrow(),
                                        product.selectOption("版本", "8GB 128GB").orElseThrow()))
                                .position(1).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("8bf9144ad8c26840.jpg"), resolveImageUrl("6f728a6562668d8f.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "日出印象").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
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
                .categories(List.of("1"))
                .brandId("1")
                //
                .imageUrl(resolveImageUrl("3c5048ac3b93dcca.png"))
                .imageUrl(resolveImageUrl("3c5048ac3b93dccc.jpg"))
                .imageUrl(resolveImageUrl("ec48ee3a1e78a5ce.jpg"))
                .imageUrl(resolveImageUrl("178e05db88b4477e.jpg"))
                //
                .fixedShippingCost(30)
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("90").province("山东省")
                        .cityId("1428").city("青岛市")
                        .countyId("11202").county("市南区")
                        .build())
                //
                .adjustMonthlySales(50)
                .adjustTotalSales(50)
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
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "深空流光").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "深空流光").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.jpg"), resolveImageUrl("178e05db88b4477e.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "液态天河").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(1).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.jpg"), resolveImageUrl("178e05db88b4477e.jpg")))
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
                .categories(List.of("1"))
                .brandId("1")
                //
                .imageUrl(resolveImageUrl("47fdb0779e7dad8a.jpg"))
                .imageUrl(resolveImageUrl("5cdd0ce2N5852750d.jpg"))
                .imageUrl(resolveImageUrl("0d0601b02dbb38e9.jpg"))
                .imageUrl(resolveImageUrl("5cdd0d93N3d7e0776.jpg"))
                .imageUrl(resolveImageUrl("a95c82b7c278fe1a.jpg"))
                .imageUrl(resolveImageUrl("3e4ee6b91564649d.jpg"))
                //
                .freeShipping()
                //
                .origin(product -> product.createOrigin().toBuilder()
                        .provinceId("79").province("湖北省")
                        .cityId("1314").city("武汉市")
                        .countyId("10119").county("江岸区")
                        .build())
                //
                .adjustMonthlySales(60)
                .adjustTotalSales(60)
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
                        .toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.jpg"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                        .optionSelections(List.of(
                                product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                product.selectOption("版本", "6GB 128GB").orElseThrow()))
                        .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.jpg"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.jpg"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "曜岩灰").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.jpg"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "6GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.jpg"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.jpg"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "星雾蓝").orElseThrow(),
                                        product.selectOption("版本", "12GB 256GB").orElseThrow()))
                                .position(0).build())

                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.jpg"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "皓月金").orElseThrow(),
                                        product.selectOption("版本", "6GB 128GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.jpg"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                                .optionSelections(List.of(
                                        product.selectOption("颜色", "皓月金").orElseThrow(),
                                        product.selectOption("版本", "8GB 256GB").orElseThrow()))
                                .position(0).build())
                .variant(product ->
                        product.createVariant(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME)).toBuilder().retailPrice(price).price(price).adjustInventoryQuantity(100)
                                .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.jpg"), resolveImageUrl("3e4ee6b91564649d.jpg")))
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
