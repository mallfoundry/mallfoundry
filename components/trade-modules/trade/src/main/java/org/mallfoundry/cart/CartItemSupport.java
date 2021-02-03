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

package org.mallfoundry.cart;

public abstract class CartItemSupport implements MutableCartItem {

    @Override
    public void adjustQuantity(int quantityDelta) throws CartException {
        var adjustedQuantity = this.getQuantity() + quantityDelta;
        if (adjustedQuantity < 0) {
            throw new CartException("The adjusted quantity cannot be less than zero");
        }

        this.setQuantity(adjustedQuantity);
    }

    @Override
    public void check() {
        this.setChecked(true);
    }

    @Override
    public void uncheck() {
        this.setChecked(false);
    }
}
