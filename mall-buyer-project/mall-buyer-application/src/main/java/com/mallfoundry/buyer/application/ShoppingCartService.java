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

package com.mallfoundry.buyer.application;

import com.mallfoundry.buyer.domain.PurchaseOrder;
import com.mallfoundry.buyer.domain.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public ShoppingCartService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional
    public void addOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.save(order);
    }

    public void removeOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.delete(order.getId());
    }

    public void updateOrder(PurchaseOrder order) {
        this.purchaseOrderRepository.update(order);
    }

    public List<PurchaseOrder> getOrders(String cart) {
        return this.purchaseOrderRepository.findListByCart(cart);
    }
}
