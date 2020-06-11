package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface CustomerAddress {

    String getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getTag();

    void setTag(String tag);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getZip();

    void setZip(String zip);

    String getLocation();

    void setLocation(String location);

    String getAddress();

    void setAddress(String address);

    boolean isDefaulted();

    void setDefaulted(boolean defaulted);

    Date getCreatedTime();

    default BuilderSupport toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<CustomerAddress> {

        Builder firstName(String firstName);

        Builder lastName(String lastName);

        Builder countryCode(String countryCode);

        Builder mobile(String mobile);

        Builder zip(String zip);

        Builder location(String location);

        Builder address(String address);

        Builder defaulted(boolean defaulted);

        default Builder defaulted() {
            return this.defaulted(true);
        }
    }

    abstract class BuilderSupport implements Builder {

        private final CustomerAddress address;

        public BuilderSupport(CustomerAddress address) {
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

        public Builder zip(String zip) {
            this.address.setZip(zip);
            return this;
        }

        public Builder location(String location) {
            this.address.setLocation(location);
            return this;
        }

        public Builder address(String address) {
            this.address.setAddress(address);
            return this;
        }

        public Builder defaulted(boolean defaulted) {
            this.address.setDefaulted(defaulted);
            return this;
        }

        public CustomerAddress build() {
            return this.address;
        }
    }
}
