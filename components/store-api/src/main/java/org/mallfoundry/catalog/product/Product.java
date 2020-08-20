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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 商品对象是一个基本单元，它包含了基本属性和商品变体。
 *
 * @author Zhi Tang
 */
public interface Product extends ProductBody, StoreOwnership {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    ProductStatus getStatus();

    void setStatus(ProductStatus status);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    String getCategoryId();

    void setCategoryId(String categoryId);

    String getBrandId();

    void setBrandId(String brandId);

    Set<String> getCollections();

    void setCollections(Set<String> collections);

    long getTotalSales();

    /**
     * 调整商品的总销售量，参数可是是正数也可以是负数。
     * 正数表示在此商品总销量的基础上增加给定参数的销量，负数则是在此商品总销量的基础上减少给定参数的销量。
     *
     * @param sales 需要调整的数量
     * @throws ProductException 如果调整后的数量小于零
     */
    void adjustTotalSales(long sales) throws ProductException;

    long getMonthlySales();

    /**
     * 调整商品的月售量，参数可是是正数也可以是负数。
     * 正数表示在此商品月销量的基础上增加给定参数的销量，负数则是在此商品月销量的基础上减少给定参数的销量。
     *
     * @param sales 需要调整的数量
     * @throws ProductException 如果调整后的数量小于零
     */
    void adjustMonthlySales(long sales) throws ProductException;

    void addImageUrl(String imageUrl);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    void removeImageUrl(String imageUrl);

    void addVideoUrl(String url);

    List<String> getVideoUrls();

    void setVideoUrls(List<String> videoUrls);

    void removeVideoUrl(String url);

    ProductOrigin createOrigin();

    ProductOrigin getOrigin();

    void setOrigin(ProductOrigin origin);

    boolean isFreeShipping();

    void freeShipping();

    BigDecimal getFixedShippingCost();

    void setFixedShippingCost(BigDecimal fixedShippingCost) throws ProductException;

    String getShippingRateId();

    void setShippingRateId(String shippingRateId) throws ProductException;

    /**
     * 获得商品库存数量，商品库存数量并不是商品本身的数量，而是库存数量最少的商品变体的数量。
     *
     * @return 库存数量
     */
    int getInventoryQuantity();

    /**
     * 调整商品变体的库存数量，参数可是是正数也可以是负数。
     * 正数表示在此商品变体库存数量的基础上增加给定参数的销量，负数则是在此商品变体库存数量的基础上减少给定参数的销量。
     *
     * @param variantId     商品变体标识
     * @param quantityDelta 需要调整的数量
     * @throws ProductException 如果调整后的数量小于零
     */
    void adjustInventoryQuantity(String variantId, int quantityDelta) throws ProductException;

    InventoryStatus getInventoryStatus();

    ProductVariant createVariant(String id);

    void addVariant(ProductVariant variant);

    void addVariants(List<ProductVariant> variants);

    /*void updateVariants(List<ProductVariant> variants);*/

    List<ProductVariant> getVariants();

    ProductVariant getVariant(String variantId);

    Optional<ProductVariant> findVariant(String variantId);

    Optional<ProductVariant> selectionVariant(List<OptionSelection> selections);

    void removeVariant(ProductVariant variant);

    void removeVariants(List<ProductVariant> variants);

    void clearVariants();

    ProductOption createOption(String id);

    void addOption(ProductOption option);

    List<ProductOption> getOptions();

    Optional<ProductOption> getOption(String name);

    Optional<OptionSelection> selectOption(String name, String label);

    void updateOptions(List<ProductOption> options);

    ProductAttribute createAttribute();

    ProductAttribute createAttribute(String name, String value);

    ProductAttribute createAttribute(String namespace, String name, String value);

    void addAttribute(ProductAttribute attribute);

    void addAttributes(List<ProductAttribute> attributes);

    Optional<ProductAttribute> getAttribute(String namespace, String name);

    List<ProductAttribute> getAttributes();

    void removeAttribute(ProductAttribute attribute);

    void clearAttributes();

    Date getCreatedTime();

    Date getPublishedTime();

    /**
     * 创建商品对象。
     */
    void create();

    /**
     * 发布商品对象。
     */
    void publish();

    /**
     * 取消发布对象。
     */
    void unpublish();

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Product> {

        Builder storeId(String storeId);

        Builder name(String name);

        Builder description(String description);

        Builder type(ProductType type);

        Builder status(ProductStatus status);

        Builder categoryId(String categoryId);

        Builder brandId(String brandId);

        Builder freeShipping();

        Builder freeShipping(Boolean freeShipping);

        Builder fixedShippingCost(BigDecimal fixedShippingCost);

        Builder fixedShippingCost(double fixedShippingCost);

        Builder shippingRateId(String shippingRateId);

        Builder origin(ProductOrigin origin);

        Builder origin(Function<Product, ProductOrigin> function);

        Builder origin(Supplier<ProductOrigin> supplier);

        Builder adjustTotalSales(long sales);

        Builder adjustMonthlySales(long sales);

        Builder collections(Set<String> collections);

        Builder body(String body);

        Builder imageUrl(String image);

        Builder imageUrls(List<String> images);

        Builder videoUrl(String video);

        Builder videoUrls(List<String> videos);

        Builder option(ProductOption option);

        Builder option(Function<Product, ProductOption> function);

        Builder options(List<ProductOption> options);

        Builder options(Function<Product, List<ProductOption>> function);

        Builder variant(ProductVariant variant);

        Builder variant(Function<Product, ProductVariant> variant);

        Builder variants(Function<Product, List<ProductVariant>> function);

        Builder variants(Supplier<List<ProductVariant>> supplier);

        Builder attribute(ProductAttribute attribute);

        Builder attribute(Function<Product, ProductAttribute> attribute);

        Builder attributes(Function<Product, List<ProductAttribute>> function);

        Builder attributes(Supplier<List<ProductAttribute>> supplier);

        Builder create();

        Builder publish();

        Builder unpublish();
    }
}
