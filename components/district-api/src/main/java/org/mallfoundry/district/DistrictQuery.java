package org.mallfoundry.district;

public interface DistrictQuery {

    String getCountryId();

    void setCountryId(String countryId);

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

        public Builder scope(byte scope) {
            this.query.setScope(scope);
            return this;
        }

        public DistrictQuery build() {
            return this.query;
        }
    }

}
