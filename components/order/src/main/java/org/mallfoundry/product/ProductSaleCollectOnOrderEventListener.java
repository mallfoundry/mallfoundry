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

package org.mallfoundry.product;

import org.mallfoundry.catalog.product.sales.ProductSale;
import org.mallfoundry.catalog.product.sales.ProductSaleService;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrdersPaidEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ProductSaleCollectOnOrderEventListener {

    private final ProductSaleService productSaleService;

    public ProductSaleCollectOnOrderEventListener(ProductSaleService productSaleService) {
        this.productSaleService = productSaleService;
    }

    private ProductSale createProductSale(Order order, OrderItem item) {
        var paidDate = LocalDate.from(order.getPaidTime().toInstant());
        return this.productSaleService.createProductSale().toBuilder()
                .productId(item.getProductId()).variantId(item.getVariantId())
                .year((short) paidDate.getYear()).month((byte) paidDate.getMonthValue()).dayOfMonth((byte) paidDate.getDayOfMonth())
                .totalAmounts(item.getTotalAmount()).totalQuantities(item.getQuantity())
                .build();
    }

    private List<ProductSale> createProductSales(Order order) {
        return order.getItems().stream().map(item -> this.createProductSale(order, item))
                .collect(Collectors.toUnmodifiableList());
    }

    //    @Async
    @EventListener
    public void onOrdersPaidEvent(OrdersPaidEvent event) {
        var sales = event.getOrders().stream()
                .map(this::createProductSales).flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
        this.productSaleService.adjustProductSales(sales);
    }
}
