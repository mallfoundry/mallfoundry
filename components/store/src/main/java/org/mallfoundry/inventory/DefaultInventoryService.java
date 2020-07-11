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

package org.mallfoundry.inventory;

import org.mallfoundry.catalog.product.ProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultInventoryService implements InventoryService {

    private final ProductService productService;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultInventoryService(ProductService productService,
                                   ApplicationEventPublisher eventPublisher) {
        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public InventoryAdjustment createInventoryAdjustment() {
        return new DefaultInventoryAdjustment();
    }

    private void publishInventoryAdjustedEvent(InventoryAdjustment adjustment) {
        this.eventPublisher.publishEvent(new ImmutableInventoryAdjustedEvent(adjustment));
    }

    @Override
    public void adjustInventory(InventoryAdjustment adjustment) {
        this.productService.adjustProductInventory(adjustment);
        this.publishInventoryAdjustedEvent(adjustment);
    }

    @Override
    public void adjustInventories(List<InventoryAdjustment> adjustments) {
        this.productService.adjustProductInventories(adjustments);
        adjustments.forEach(this::publishInventoryAdjustedEvent);
    }

}
