/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.order;

import com.mallfoundry.store.product.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckoutCounter {

    private final ProductService productService;

    public CheckoutCounter(ProductService productService) {
        this.productService = productService;
    }

    public void checkout(Order order) throws CheckoutException {
        try {
            List<OrderItem> items = order.getItems();
            for (OrderItem item : items) {
                com.mallfoundry.store.product.Product product = this.productService.getProduct(item.getProductId()).orElseThrow();
                com.mallfoundry.store.product.ProductVariant variant = product.getVariant(item.getVariantId()).orElseThrow();
                variant.decrementInventoryQuantity(item.getQuantity());
            }
        } catch (Exception e) {
            throw new CheckoutException(e);
        }
    }
}
