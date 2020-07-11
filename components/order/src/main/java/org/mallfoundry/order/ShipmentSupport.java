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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ShipmentSupport implements Shipment {

    @Override
    public ShipmentItem createItem(String id) {
        return new DefaultShipmentItem();
    }

    @Override
    public void addItem(ShipmentItem item) {
        this.getItems().add(item);
    }

    @Override
    public Optional<ShipmentItem> getItem(String id) {
        return this.getItems().stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public void removeItem(ShipmentItem item) {
        this.getItems().remove(item);
    }

    @Override
    public List<String> getImageUrls() {
        return this.getItems().stream().map(ShipmentItem::getImageUrl).collect(Collectors.toUnmodifiableList());
    }
}
