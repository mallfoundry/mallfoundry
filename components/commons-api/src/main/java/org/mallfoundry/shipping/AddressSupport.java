package org.mallfoundry.shipping;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public abstract class AddressSupport implements Address {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final Address address;

        protected BuilderSupport(Address address) {
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

        public Address build() {
            return this.address;
        }
    }
}
