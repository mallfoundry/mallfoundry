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

package com.mallfoundry.customer.infrastructure.persistence.mybatis.cart;

import com.mallfoundry.customer.domain.cart.CartItem;
import com.mallfoundry.customer.domain.cart.CartItemRepository;
import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.storefront.application.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemRepositoryMybatis implements CartItemRepository {

    private final CartItemMapper cartMapper;

    private final ProductService productService;

    public CartItemRepositoryMybatis(CartItemMapper cartMapper,
                                     ProductService productService) {
        this.cartMapper = cartMapper;
        this.productService = productService;
    }

    @Override
    public void add(CartItem item) {
        if (StringUtils.isEmpty(item.getId())) {
            item.setId(this.nextOrderId());
        }
        item.setCreateTimeIfNull();
        this.cartMapper.insert(item);
    }

    @Override
    public void update(CartItem item) {
        this.cartMapper.update(item);
    }

    @Override
    public void delete(String cart, String itemId) {
        this.cartMapper.deleteById(itemId);
    }

    @Override
    public List<CartItem> findAllByCart(String cart) {
//        List<CartItem> items = this.cartMapper.selectListByCart(cart);
//        if (CollectionUtils.isEmpty(items)) {
//            return Collections.emptyList();
//        }
//
//        List<String> productIds = items.stream().map(CartItem::getProductId).collect(Collectors.toList());
//        List<Product> products = this.productService.getProducts(productIds);
//        if (CollectionUtils.isNotEmpty(products)) {
//            Map<String, Product> keyedProducts = products.stream().collect(Collectors.toMap(Product::getId, product -> product));
//            items.forEach(item -> {
//                item.setProduct(keyedProducts.get(item.getProductId()));
//            });
//        }
//
//        return items;

        return this.cartMapper.selectListByCart(cart);
    }

    private String nextOrderId() {
        long nextOrderId = PrimaryKeyHolder.sequence().nextVal("customer.cart.item.id");
        return String.valueOf(10000000000000L + nextOrderId);
    }
}
