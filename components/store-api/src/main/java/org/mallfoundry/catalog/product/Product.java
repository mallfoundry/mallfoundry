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
import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
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
public interface Product extends Serializable {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    ProductStatus getStatus();

    void setStatus(ProductStatus status);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    /**
     * 获得关联商品类目标识。
     *
     * @return 商品类目标识
     */
    String getCategoryId();

    void setCategoryId(String categoryId);

    /**
     * 获得商品品牌。
     *
     * @return 商品品牌标识。
     */
    String getBrandId();

    void setBrandId(String brandId);

    /**
     * 获得商家自定义的商品类目集合。
     *
     * @return 商家自定义的商品类目集合
     */
    Set<String> getCollections();

    void setCollections(Set<String> collections);

    /**
     * 获得商品的总销售量。
     *
     * @return 商品的总销售量
     */
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

    /**
     * 添加一个商品图片URL链接。
     *
     * @param imageUrl 商品图片URL
     */
    void addImageUrl(String imageUrl);

    /**
     * 获得商品图片URL链接集合。
     *
     * @return 商品图片URL链接集合
     */
    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    /**
     * 删除给定的商品图片URL链接。
     *
     * @param imageUrl 商品图片URL
     */
    void removeImageUrl(String imageUrl);

    void addVideoUrl(String url);

    List<String> getVideoUrls();

    void setVideoUrls(List<String> videoUrls);

    void removeVideoUrl(String url);

    /**
     * 创建一个商品发货地址。
     *
     * @return 商品发货地址实例
     */
    ProductShippingOrigin createShippingOrigin();

    /**
     * 获得商品关联的发货地址。
     *
     * @return 商品发货地址实例
     */
    ProductShippingOrigin getShippingOrigin();

    void setShippingOrigin(ProductShippingOrigin shippingOrigin);

    /**
     * 获得商品是否包邮。
     *
     * @return {@code true} 表示包邮，{@code false} 表示不包邮
     */
    boolean isFreeShipping();

    /**
     * 设置商品包邮，调用此方法后 {@link #getFixedShippingCost()}
     * 和 {@link #getShippingRateId()} 将被设置为 null。
     */
    void freeShipping();

    /**
     * 获得固定运费。
     *
     * @return 固定运费
     */
    BigDecimal getFixedShippingCost();

    /**
     * 设置固定运费。
     *
     * @param fixedShippingCost 固定运费
     * @throws ProductException 如果运费为 null
     */
    void setFixedShippingCost(BigDecimal fixedShippingCost) throws ProductException;

    /**
     * 获得商品所关联的商家自定义的运费对象标识。
     *
     * @return 运费标识。
     */
    String getShippingRateId();

    /**
     * 设置商家自定义的运费对象标识。
     *
     * @param shippingRateId 运费对象标识
     * @throws ProductException 如果运费对象标识为 null
     */
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

    List<ProductVariant> getVariants();

    Optional<ProductVariant> getVariant(String variantId);

    Optional<ProductVariant> selectionVariant(List<OptionSelection> selections);

    void removeVariant(ProductVariant variant);

    void clearVariants();

    ProductOption createOption(String id);

    void addOption(ProductOption option);

    void addOptions(List<ProductOption> options);

    /**
     * 获得商品的规格选项集合。
     *
     * @return 商品的规格选项集合
     */
    List<ProductOption> getOptions();

    Optional<ProductOption> getOption(String name);

    Optional<OptionSelection> selectOption(String name, String label);

    void removeOption(ProductOption option);

    void clearOptions();

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

        Builder shippingOrigin(ProductShippingOrigin shippingOrigin);

        Builder shippingOrigin(Function<Product, ProductShippingOrigin> function);

        Builder shippingOrigin(Supplier<ProductShippingOrigin> supplier);

        Builder adjustTotalSales(long sales);

        Builder adjustMonthlySales(long sales);

        Builder collections(Set<String> collections);

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
