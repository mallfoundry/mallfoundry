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
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Shipment {

    String getId();

    void setId(String id);

    String getConsignorId();

    void setConsignorId(String consignorId);

    String getConsignor();

    void setConsignor(String consignor);

    List<String> getImageUrls();

    ShipmentItem createItem(String id);

    Optional<ShipmentItem> getItem(String id);

    void addItem(ShipmentItem item);

    void removeItem(ShipmentItem item);

    List<ShipmentItem> getItems();

    void setItems(List<ShipmentItem> items);

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    CarrierCode getShippingProvider();

    void setShippingProvider(CarrierCode shippingProvider);

    String getTrackingCarrier();

    void setTrackingCarrier(String trackingCarrier);

    String getShippingMethod();

    void setShippingMethod(String shippingMethod);

    String getTrackingNumber();

    void setTrackingNumber(String trackingNumber);

    Date getShippedTime();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Shipment> {

        Builder items(List<ShipmentItem> items);

        Builder consignorId(String consignorId);

        Builder consignor(String consignor);

        Builder shippingAddress(Address shippingAddress);

        Builder shippingProvider(CarrierCode shippingProvider);

        Builder trackingCarrier(String trackingCarrier);

        Builder shippingMethod(String shippingMethod);

        Builder trackingNumber(String trackingNumber);
    }

    abstract class BuilderSupport implements Builder {

        protected final Shipment shipment;

        public BuilderSupport(Shipment shipment) {
            this.shipment = shipment;
        }

        @Override
        public Builder items(List<ShipmentItem> items) {
            this.shipment.setItems(items);
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
        public Shipment build() {
            return this.shipment;
        }
    }
}
