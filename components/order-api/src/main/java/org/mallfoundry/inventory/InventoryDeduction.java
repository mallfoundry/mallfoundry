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

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 商品库存扣减方式，用于订单模块与商品模块集成时扣减商品库存数量的方式。
 *
 * @author Zhi Tang
 */
public enum InventoryDeduction {
    /**
     * 下单后扣减商品库存。
     */
    PLACED,
    /**
     * 支付后扣减商品库存。
     */
    PAID;

    public boolean isPlaced() {
        return this == PLACED;
    }

    public boolean isPaid() {
        return this == PAID;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
