package org.mallfoundry.catalog.product;

import org.mallfoundry.util.ObjectBuilder;

public interface ProductShippingOrigin {

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

    Builder toBuilder();

    @Override
    int hashCode();

    @Override
    boolean equals(Object o);

    interface Builder extends ObjectBuilder<ProductShippingOrigin> {

        Builder provinceId(String provinceId);

        Builder province(String province);

        Builder cityId(String cityId);

        Builder city(String city);

        Builder countyId(String countyId);

        Builder county(String county);
    }
}
