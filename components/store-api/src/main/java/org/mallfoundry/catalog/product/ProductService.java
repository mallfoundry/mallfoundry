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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 商品服务。
 *
 * @author Zhi Tang
 */
public interface ProductService {

    /**
     * 创建商品查询对象。
     *
     * @return 商品查询对象
     */
    ProductQuery createProductQuery();

    /**
     * 创建一个商品对象。
     *
     * @param id 商品标识
     * @return 商品对象
     */
    Product createProduct(String id);

    /**
     * 添加一个商品对象。
     *
     * @param product 商品对象
     * @return 添加后的商品对象
     */
    Product addProduct(Product product);

    Product getProduct(String id);

    Optional<Product> findProduct(String id);

    List<Product> getProducts(Collection<String> ids);

    SliceList<Product> getProducts(ProductQuery query);

    /**
     * 更新商品对象。
     *
     * @param product 商品对象
     * @return 更新后的商品对象
     */
    Product updateProduct(Product product);

    /**
     * 发布商品对象。
     *
     * @param id 商品标识
     */
    void publishProduct(String id) throws ProductException;

    void publishProducts(Set<String> ids) throws ProductException;

    void unpublishProduct(String id) throws ProductException;

    void unpublishProducts(Set<String> ids) throws ProductException;

    /**
     * 调整商品库存。
     *
     * @param adjustment 库存调整对象。
     */
    void adjustProductInventory(InventoryAdjustment adjustment);

    void adjustProductInventories(List<InventoryAdjustment> adjustments);

    void deleteProduct(String id);
}
