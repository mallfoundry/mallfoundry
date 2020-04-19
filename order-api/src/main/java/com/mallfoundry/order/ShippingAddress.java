package com.mallfoundry.order;

import java.io.Serializable;

public interface ShippingAddress extends Serializable {

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getPostalCode();

    void setPostalCode(String postalCode);

    String getAddress();

    void setAddress(String address);

    String getLocation();

    void setLocation(String location);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {
        private final ShippingAddress address;

        public Builder(ShippingAddress address) {
            this.address = address;
        }

        public Builder firstName(String firstName) {
            this.address.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            this.address.setLastName(lastName);
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.address.setCountryCode(countryCode);
            return this;
        }

        public Builder mobile(String mobile) {
            this.address.setMobile(mobile);
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.address.setPostalCode(postalCode);
            return this;
        }

        public Builder address(String address) {
            this.address.setAddress(address);
            return this;
        }

        public Builder location(String location) {
            this.address.setLocation(location);
            return this;
        }

        public ShippingAddress build() {
            return this.address;
        }
    }
}
