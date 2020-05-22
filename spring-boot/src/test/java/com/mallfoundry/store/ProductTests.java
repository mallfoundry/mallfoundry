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


import com.mallfoundry.StaticServer;
import com.mallfoundry.store.product.InternalProduct;
import com.mallfoundry.store.product.InternalProductAttribute;
import com.mallfoundry.store.product.InternalProductService;
import com.mallfoundry.store.product.ProductStatus;
import com.mallfoundry.store.product.ProductType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductTests {


    @Autowired
    private InternalProductService productService;

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

        productService.saveProduct(this.newProduct1());

        productService.saveProduct(this.newProduct2());
        productService.saveProduct(this.newProduct2_1());
        productService.saveProduct(this.newProduct3());
        productService.saveProduct(this.newProduct4());
        productService.saveProduct(this.newProduct5());
    }

    private String resolveImageUrl(String id) {
        return StaticServer.BASE_URL + "/images/" + id;
    }

    private InternalProduct newProduct1() {
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        double price = 0.01;
        product.setStoreId("huawei");
        product.setName("华为 HUAWEI Mate 30 Pro 5G 麒麟990 OLED环幕屏双4000万徕卡电影四摄手机");
        product.setType(ProductType.PHYSICAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.create();

        product.addImageUrl(resolveImageUrl("e070a0bc693efc85.jpg"));
        product.addImageUrl(resolveImageUrl("cd96fb7761beeb9e.jpg"));
        product.addImageUrl(resolveImageUrl("c78c80a4116ee57d.jpg"));
        product.addImageUrl(resolveImageUrl("777b12adea1822f6.jpg"));

        product.createOption("颜色").addSimpleValues("翡冷翠", "丹霞橙");
        product.createOption("版本").addSimpleValues("8GB 128GB", "12GB 256GB");
        product.setCreatedTime(new Date());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionValues(List.of("翡冷翠", "8GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("e070a0bc693efc85.jpg"), resolveImageUrl("cd96fb7761beeb9e.jpg")))
                        .optionValues(List.of("翡冷翠", "12GB 256GB")).position(0).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("c78c80a4116ee57d.jpg"), resolveImageUrl("777b12adea1822f6.jpg")))
                        .optionValues(List.of("丹霞橙", "8GB 128GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("c78c80a4116ee57d.jpg"), resolveImageUrl("777b12adea1822f6.jpg")))
                        .optionValues(List.of("丹霞橙", "12GB 256GB")).position(1).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

    private InternalProduct newProduct2() {
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        double price = 0.04;
        product.setStoreId("mi");
        product.setName("小米9 Pro 5G 骁龙855Plus 30W无线闪充手机");
        product.setCreatedTime(new Date());
        product.setType(ProductType.PHYSICAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.setCollectionIds(Set.of("12236", "12243"));
        product.create();

        product.addImageUrl(resolveImageUrl("da769739c0a75afb.jpg"));
        product.addImageUrl(resolveImageUrl("753768612ae90b4e.jpg"));
        product.addImageUrl(resolveImageUrl("cd769d2bd022de2a.jpg"));
        product.addImageUrl(resolveImageUrl("62edde5e1ef2fd85.jpg"));

        product.createOption("颜色").addSimpleValues("梦之白", "钛银黑");
        product.createOption("版本").addSimpleValues("8GB 128GB", "12GB 256GB");

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.04).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                        .optionValues(List.of("梦之白", "8GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.06).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("da769739c0a75afb.jpg"), resolveImageUrl("753768612ae90b4e.jpg")))
                        .optionValues(List.of("梦之白", "12GB 256GB")).position(1).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.08).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("cd769d2bd022de2a.jpg"), resolveImageUrl("62edde5e1ef2fd85.jpg")))
                        .optionValues(List.of("钛银黑", "8GB 128GB")).position(2).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.1).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("cd769d2bd022de2a.jpg"), resolveImageUrl("62edde5e1ef2fd85.jpg")))
                        .optionValues(List.of("钛银黑", "12GB 256GB")).position(3).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

    private InternalProduct newProduct2_1() {
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        double price = 0.02;
        product.setStoreId("mi");
        product.setName("【向往的生活同款】小米10 双模5G 骁龙865 1亿像素8K电影相机 对称式立体声");
        product.setCreatedTime(new Date());
        product.setType(ProductType.DIGITAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.setCollectionIds(Set.of("12230", "12243"));
        product.create();

        product.addImageUrl(resolveImageUrl("6626003a708ce8ef.jpg"));
        product.addImageUrl(resolveImageUrl("df797544001e5ba3.jpg"));
        product.addImageUrl(resolveImageUrl("979bde1d68c3de5b.jpg"));
        product.addImageUrl(resolveImageUrl("0ad4ba55c20c6903.jpg"));
        product.addImageUrl(resolveImageUrl("e523be52552921bf.jpg"));
        product.addImageUrl(resolveImageUrl("6765a52c369741ec.jpg"));

        product.createOption("颜色").addSimpleValues("钛银黑", "冰海蓝", "蜜桃金");
        product.createOption("版本").addSimpleValues("8GB 128GB", "8GB 256GB", "12GB 256GB");

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.1).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                        .optionValues(List.of("钛银黑", "8GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.12).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                        .optionValues(List.of("钛银黑", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.14).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6626003a708ce8ef.jpg"), resolveImageUrl("df797544001e5ba3.jpg"), resolveImageUrl("979bde1d68c3de5b.jpg")))
                        .optionValues(List.of("钛银黑", "12GB 256GB")).position(1).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.16).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                        .optionValues(List.of("冰海蓝", "8GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(0.18).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                        .optionValues(List.of("冰海蓝", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0ad4ba55c20c6903.jpg"), resolveImageUrl("e523be52552921bf.jpg"), resolveImageUrl("6765a52c369741ec.jpg")))
                        .optionValues(List.of("冰海蓝", "12GB 256GB")).position(1).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                        .optionValues(List.of("蜜桃金", "8GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                        .optionValues(List.of("蜜桃金", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("6ec29e79b28d7c0b.jpg"), resolveImageUrl("05fdde3b515dae87.jpg"), resolveImageUrl("e7233f1b0e4b0c45.jpg")))
                        .optionValues(List.of("蜜桃金", "12GB 256GB")).position(1).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

    private InternalProduct newProduct3() {
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        double price = 0.01;
        product.setStoreId("oppo");
        product.setName("OPPO Reno3 Pro 一体化双模5G 视频双防抖 骁龙765G 7.7mm轻薄机身手机");
        product.setType(ProductType.PHYSICAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedTime(new Date());

        product.addImageUrl(resolveImageUrl("0ea203c122fb3dae.jpg"));
        product.addImageUrl(resolveImageUrl("7eac762ed4bcb66f.jpg"));
        product.addImageUrl(resolveImageUrl("b72e95f6953e2f3c.jpg"));
        product.addImageUrl(resolveImageUrl("c682d018d572c792.jpg"));
        product.addImageUrl(resolveImageUrl("8bf9144ad8c26840.jpg"));
        product.addImageUrl(resolveImageUrl("6f728a6562668d8f.jpg"));

        product.createOption("颜色").addSimpleValues("雾月白", "日出印象", "蓝色星夜");
        product.createOption("版本").addSimpleValues("8GB 256GB", "12GB 256GB");

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                        .optionValues(List.of("雾月白", "8GB 256GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0ea203c122fb3dae.jpg"), resolveImageUrl("7eac762ed4bcb66f.jpg")))
                        .optionValues(List.of("雾月白", "12GB 256GB")).position(0).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                        .optionValues(List.of("日出印象", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("b72e95f6953e2f3c.jpg"), resolveImageUrl("c682d018d572c792.jpg")))
                        .optionValues(List.of("日出印象", "12GB 256GB")).position(1).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("8bf9144ad8c26840.jpg"), resolveImageUrl("6f728a6562668d8f.jpg")))
                        .optionValues(List.of("蓝色星夜", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("8bf9144ad8c26840.jpg"), resolveImageUrl("6f728a6562668d8f.jpg")))
                        .optionValues(List.of("蓝色星夜", "12GB 256GB")).position(1).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

    private InternalProduct newProduct4() {
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        double price = 0.01;
        product.setStoreId("vivo");
        product.setName("vivo NEX3 无界瀑布屏 高通骁龙855Plus 6400万三摄5G全网通手机");
        product.setType(ProductType.PHYSICAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedTime(new Date());

        product.addImageUrl(resolveImageUrl("3c5048ac3b93dcca.png"));
        product.addImageUrl(resolveImageUrl("3c5048ac3b93dccc.jpg"));
        product.addImageUrl(resolveImageUrl("ec48ee3a1e78a5ce.jpg"));
        product.addImageUrl(resolveImageUrl("178e05db88b4477e.jpg"));

        product.createOption("颜色").addSimpleValues("深空流光", "液态天河");
        product.createOption("版本").addSimpleValues("8GB 256GB", "12GB 256GB");

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                        .optionValues(List.of("深空流光", "8GB 256GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("3c5048ac3b93dcca.png"), resolveImageUrl("3c5048ac3b93dccc.jpg")))
                        .optionValues(List.of("深空流光", "12GB 256GB")).position(0).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.png"), resolveImageUrl("178e05db88b4477e.jpg")))
                        .optionValues(List.of("液态天河", "8GB 256GB")).position(1).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("ec48ee3a1e78a5ce.png"), resolveImageUrl("178e05db88b4477e.jpg")))
                        .optionValues(List.of("液态天河", "12GB 256GB")).position(1).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

    private InternalProduct newProduct5() {
        double price = 0.01;
        InternalProduct product = (InternalProduct) this.productService.createProduct();
        product.setStoreId("one plus");
        product.setName("一加 OnePlus 7 Pro 2K+90Hz 流体屏 骁龙855旗舰 4800万超广角三摄手机");
        product.setType(ProductType.PHYSICAL);
        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedTime(new Date());

        product.addImageUrl(resolveImageUrl("47fdb0779e7dad8a.jpg"));
        product.addImageUrl(resolveImageUrl("5cdd0ce2N5852750d.jpg"));
        product.addImageUrl(resolveImageUrl("0d0601b02dbb38e9.jpg"));
        product.addImageUrl(resolveImageUrl("5cdd0d93N3d7e0776.jpg"));
        product.addImageUrl(resolveImageUrl("a95c82b7c278fe1a.jpg"));
        product.addImageUrl(resolveImageUrl("3e4ee6b91564649d.jpg"));

        product.createOption("颜色").addSimpleValues("曜岩灰", "星雾蓝", "皓月金");
        product.createOption("版本").addSimpleValues("6GB 128GB", "8GB 256GB", "12GB 256GB");

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                        .optionValues(List.of("曜岩灰", "6GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                        .optionValues(List.of("曜岩灰", "8GB 256GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("47fdb0779e7dad8a.png"), resolveImageUrl("5cdd0ce2N5852750d.jpg")))
                        .optionValues(List.of("曜岩灰", "12GB 256GB")).position(0).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                        .optionValues(List.of("星雾蓝", "6GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                        .optionValues(List.of("星雾蓝", "8GB 256GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("0d0601b02dbb38e9.png"), resolveImageUrl("5cdd0d93N3d7e0776.jpg")))
                        .optionValues(List.of("星雾蓝", "12GB 256GB")).position(0).build());

        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                        .optionValues(List.of("皓月金", "6GB 128GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                        .optionValues(List.of("皓月金", "8GB 256GB")).position(0).build());
        product.addVariant(
                this.productService.createProductVariant().toBuilder().marketPrice(price).price(price).inventoryQuantity(100)
                        .imageUrls(List.of(resolveImageUrl("a95c82b7c278fe1a.png"), resolveImageUrl("3e4ee6b91564649d.jpg")))
                        .optionValues(List.of("皓月金", "12GB 256GB")).position(0).build());

        product.addAttribute(new InternalProductAttribute("产地", "中国"));
        product.addAttribute(new InternalProductAttribute("生成时间", "2018-10-11"));
        product.addAttribute(new InternalProductAttribute("颜色", "黑"));
        return product;
    }

}
