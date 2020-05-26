package org.mallfoundry.district;

public interface DistrictQuery {

    String getCountryId();

    void setCountryId(String countryId);

    byte getScope();

    void setScope(byte scope);


    DistrictQueryBuilder toBuilder();


    class DistrictQueryBuilder {

        private final DistrictQuery query;

        public DistrictQueryBuilder(DistrictQuery query) {
            this.query = query;
        }

        public DistrictQueryBuilder countryId(String countryId) {
            this.query.setCountryId(countryId);
            return this;
        }

        public DistrictQueryBuilder scope(byte scope) {
            this.query.setScope(scope);
            return this;
        }

        public DistrictQuery build() {
            return this.query;
        }
    }

}
