package org.mallfoundry.order;

import org.mallfoundry.shipping.Address;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

public interface Shipment {

    String getId();

    void setId(String id);

    String getOrderId();

    String getConsignorId();

    void setConsignorId(String consignorId);

    String getConsignor();

    void setConsignor(String consignor);

    List<OrderItem> getItems();

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    String getShippingProvider();

    void setShippingProvider(String shippingProvider);

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

        Builder consignorId(String consignorId);

        Builder consignor(String consignor);

        Builder shippingAddress(Address shippingAddress);

        Builder shippingProvider(String shippingProvider);

        Builder shippingMethod(String shippingMethod);

        Builder trackingNumber(String trackingNumber);
    }

    abstract class BuilderSupport implements Builder {

        protected final Shipment shipment;

        public BuilderSupport(Shipment shipment) {
            this.shipment = shipment;
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
        public Builder shippingProvider(String shippingProvider) {
            this.shipment.setShippingProvider(shippingProvider);
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
