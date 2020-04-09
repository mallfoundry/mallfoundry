package com.mallfoundry.order;

public interface BillingAddress extends AddressBase {

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {
        private BillingAddress address;

        public Builder(BillingAddress address) {
            this.address = address;
        }

        public Builder consignee(String consignee) {
            this.address.setConsignee(consignee);
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

        public BillingAddress build() {
            return this.address;
        }
    }

}
