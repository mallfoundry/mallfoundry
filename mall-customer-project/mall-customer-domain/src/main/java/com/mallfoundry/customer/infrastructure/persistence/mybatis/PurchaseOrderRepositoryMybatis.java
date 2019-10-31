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

package com.mallfoundry.customer.infrastructure.persistence.mybatis;

import com.mallfoundry.customer.domain.PurchaseOrder;
import com.mallfoundry.customer.domain.PurchaseOrderRepository;
import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseOrderRepositoryMybatis implements PurchaseOrderRepository {

    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderRepositoryMybatis(PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    @Override
    public void save(PurchaseOrder order) {
        if (StringUtils.isEmpty(order.getId())) {
            order.setId(this.nextOrderId());
        }
        order.setCreateTimeIfNull();
        this.purchaseOrderMapper.insertOrder(order);
    }

    @Override
    public void update(PurchaseOrder order) {
        this.purchaseOrderMapper.updateOrder(order);
    }

    @Override
    public void delete(String orderId) {
        this.purchaseOrderMapper.deleteOrderById(orderId);
    }

    @Override
    public List<PurchaseOrder> findListByCart(String cart) {
        return this.purchaseOrderMapper.selectListByCart(cart);
    }

    private String nextOrderId() {
        long nextOrderId = PrimaryKeyHolder.sequence().nextVal("customer.purchase.order.id");
        return String.valueOf(10000000000000L + nextOrderId);
    }
}
