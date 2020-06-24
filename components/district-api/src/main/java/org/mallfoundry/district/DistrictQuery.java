package org.mallfoundry.district;

public interface DistrictQuery {

    String getCountryId();

    void setCountryId(String countryId);

    String getProvinceId();

    void setProvinceId(String provinceId);

    String getCityId();

    void setCityId(String cityId);

    String getCode();

    void setCode(String code);

    byte getScope();

    void setScope(byte scope);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final DistrictQuery query;

        public Builder(DistrictQuery query) {
            this.query = query;
        }

        public Builder countryId(String countryId) {
            this.query.setCountryId(countryId);
            return this;
        }

        public Builder provinceId(String provinceId) {
            this.query.setProvinceId(provinceId);
            return this;
        }

        public Builder cityId(String cityId) {
            this.query.setCityId(cityId);
            return this;
        }

        public Builder code(String code) {
            this.query.setCode(code);
            return this;
        }

        public Builder scope(byte scope) {
            this.query.setScope(scope);
            return this;
        }

        public DistrictQuery build() {
            return this.query;
        }
    }

}
