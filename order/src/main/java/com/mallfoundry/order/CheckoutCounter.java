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

import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductService;
import com.mallfoundry.store.product.ProductVariant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CheckoutCounter {

    private final ProductService productService;

    public CheckoutCounter(ProductService productService) {
        this.productService = productService;
    }

    public void checkout(InternalOrder order) throws CheckoutException {
        try {
            List<InternalOrderItem> items = order.getItems();
            order.setStoreId(null);
            for (InternalOrderItem item : items) {
                Product product = this.productService.getProduct(item.getProductId()).orElseThrow();
                ProductVariant variant = product.getVariant(item.getVariantId()).orElseThrow();
                variant.decrementInventoryQuantity(item.getQuantity());

                if (Objects.isNull(order.getStoreId())) {
                    order.setStoreId(product.getStoreId().toString());
                }
            }
        } catch (Exception e) {
            throw new CheckoutException(e);
        }
        order.pending();
    }

    public void checkout(List<InternalOrder> orders) throws CheckoutException {
        for (InternalOrder order : orders) {
            this.checkout(order);
        }
    }
}
