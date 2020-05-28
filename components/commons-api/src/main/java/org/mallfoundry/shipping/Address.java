package org.mallfoundry.shipping;

import java.io.Serializable;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Address extends Serializable {

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

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

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {
        private final Address address;

        public Builder(Address address) {
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

        public Builder address(String address) {
            this.address.setAddress(address);
            return this;
        }

        public Builder location(String location) {
            this.address.setLocation(location);
            return this;
        }

        public Address build() {
            return this.address;
        }
    }
}
