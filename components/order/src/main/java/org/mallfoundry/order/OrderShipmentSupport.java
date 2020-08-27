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

import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.CarrierCode;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class OrderShipmentSupport implements MutableOrderShipment {

    @Override
    public OrderShipmentItem createItem(String id) {
        return new DefaultOrderShipmentItem(id);
    }

    @Override
    public void addItem(OrderShipmentItem item) {
        this.getItems().add(item);
    }

    @Override
    public Optional<OrderShipmentItem> getItem(String id) {
        return this.getItems().stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public void removeItem(OrderShipmentItem item) {
        this.getItems().remove(item);
    }

    @Override
    public List<String> getImageUrls() {
        return this.getItems().stream().map(OrderShipmentItem::getImageUrl).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void ship() {
        this.setShippedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        protected final OrderShipmentSupport shipment;

        protected BuilderSupport(OrderShipmentSupport shipment) {
            this.shipment = shipment;
        }

        @Override
        public Builder items(List<OrderShipmentItem> items) {
            items.forEach(this.shipment::addItem);
            return this;
        }

        @Override
        public Builder consignorId(String consignorId) {
            this.shipment.setConsignorId(consignorId);
            return this;
        }

        @Override
        public Builder consignor(String consignor) {
            this.shipment.setConsignor(consignor);
            return this;
        }

        @Override
        public Builder shippingAddress(Address shippingAddress) {
            this.shipment.setShippingAddress(shippingAddress);
            return this;
        }

        @Override
        public Builder shippingProvider(CarrierCode shippingProvider) {
            this.shipment.setShippingProvider(shippingProvider);
            return this;
        }

        @Override
        public Builder trackingCarrier(String trackingCarrier) {
            this.shipment.setTrackingCarrier(trackingCarrier);
            return this;
        }

        @Override
        public Builder shippingMethod(String shippingMethod) {
            this.shipment.setShippingMethod(shippingMethod);
            return this;
        }

        @Override
        public Builder trackingNumber(String trackingNumber) {
            this.shipment.setTrackingNumber(trackingNumber);
            return this;
        }

        @Override
        public OrderShipment build() {
            return this.shipment;
        }
    }
}
