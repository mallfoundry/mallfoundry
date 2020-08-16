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

package org.mallfoundry.order;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.mallfoundry.inventory.InventoryService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存扣减连接器，连接库存服务。
 *
 * @author Zhi Tang
 */
public class DeductingInventoryConnector implements OrderProcessor {

    private final InventoryService inventoryService;

    public DeductingInventoryConnector(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private InventoryAdjustment createInventoryAdjustment(OrderItem item) {
        return this.inventoryService.createInventoryAdjustment().toBuilder()
                .productId(item.getProductId()).variantId(item.getVariantId())
                .quantityDelta(-item.getQuantity())
                .build();
    }

    private List<InventoryAdjustment> deductingInventories(Order order) {
        var deduction = order.getInventoryDeduction();
        if ((deduction.isPlaced() && order.isPaid()) || (deduction.isPaid() && order.isPlaced())) {
            return Collections.emptyList();
        }
        return order.getItems().stream().map(this::createInventoryAdjustment).collect(Collectors.toUnmodifiableList());
    }

    private void deductingInventories(List<Order> orders) {
        var inventories = orders.stream()
                .map(this::deductingInventories)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
        // 如果订单对象的当前状态为 PLACED ，扣减库存模式为 PAID ，inventories 将为空。
        // 如果订单对象的当前状态为 PAID ，扣减库存模式为 PLACED ，inventories 将为空。
        if (CollectionUtils.isNotEmpty(inventories)) {
            this.inventoryService.adjustInventories(inventories);
        }
    }

    @Override
    public List<Order> postProcessAfterPlaceOrders(List<Order> orders) {
        this.deductingInventories(orders);
        return orders;
    }
}
