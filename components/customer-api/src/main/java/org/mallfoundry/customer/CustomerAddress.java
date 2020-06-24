package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface CustomerAddress {

    String getId();

    void setId(String id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getTag();

    void setTag(String tag);

    String getMobile();

    void setMobile(String mobile);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getProvinceId();

    void setProvinceId(String provinceId);

    String getProvince();

    void setProvince(String province);

    String getCityId();

    void setCityId(String cityId);

    String getCity();

    void setCity(String city);

    String getCountyId();

    void setCountyId(String countyId);

    String getCounty();

    void setCounty(String county);

    String getAddress();

    void setAddress(String address);

    String getZip();

    void setZip(String zip);

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

        Builder provinceId(String provinceId);

        Builder province(String province);

        Builder cityId(String cityId);

        Builder city(String city);

        Builder countyId(String countyId);

        Builder county(String county);

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

        @Override
        public Builder firstName(String firstName) {
            this.address.setFirstName(firstName);
            return this;
        }

        @Override
        public Builder lastName(String lastName) {
            this.address.setLastName(lastName);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.address.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder mobile(String mobile) {
            this.address.setMobile(mobile);
            return this;
        }

        @Override
        public Builder zip(String zip) {
            this.address.setZip(zip);
            return this;
        }

        @Override
        public Builder provinceId(String provinceId) {
            this.address.setProvinceId(provinceId);
            return this;
        }

        @Override
        public Builder province(String province) {
            this.address.setProvince(province);
            return this;
        }

        @Override
        public Builder cityId(String cityId) {
            this.address.setCityId(cityId);
            return this;
        }

        @Override
        public Builder city(String city) {
            this.address.setCity(city);
            return this;
        }

        @Override
        public Builder countyId(String countyId) {
            this.address.setCountyId(countyId);
            return this;
        }

        @Override
        public Builder county(String county) {
            this.address.setCounty(county);
            return this;
        }

        @Override
        public Builder address(String address) {
            this.address.setAddress(address);
            return this;
        }

        @Override
        public Builder defaulted(boolean defaulted) {
            this.address.setDefaulted(defaulted);
            return this;
        }

        public CustomerAddress build() {
            return this.address;
        }
    }
}
