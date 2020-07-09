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

package org.mallfoundry.rest.cart;

import lombok.Getter;
import org.mallfoundry.cart.CartItem;
import org.mallfoundry.catalog.OptionSelection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
public class CartItemResponse extends CartItemRequest {
    private final String id;
    private final String storeId;
    private final BigDecimal price;
    private final List<OptionSelection> optionSelections;
    private final Date addedTime;

    public CartItemResponse(CartItem item) {
        this.id = item.getId();
        this.storeId = item.getStoreId();
        this.price = item.getPrice();
        this.optionSelections = item.getOptionSelections();
        this.addedTime = item.getAddedTime();
        this.setProductId(item.getProductId());
        this.setVariantId(item.getVariantId());
        this.setQuantity(item.getQuantity());
        this.setName(item.getName());
        this.setImageUrl(item.getImageUrl());
        this.setChecked(item.isChecked());
    }
}
