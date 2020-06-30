package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Getter
@Setter
public class DefaultProductShippingOrigin implements ProductShippingOrigin {

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;

    public static DefaultProductShippingOrigin of(ProductShippingOrigin shippingOrigin) {
        if (shippingOrigin instanceof DefaultProductShippingOrigin) {
            return (DefaultProductShippingOrigin) shippingOrigin;
        }
        var target = new DefaultProductShippingOrigin();
        BeanUtils.copyProperties(shippingOrigin, target);
        return target;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultProductShippingOrigin)) {
            return false;
        }
        DefaultProductShippingOrigin that = (DefaultProductShippingOrigin) o;
        return Objects.equals(provinceId, that.provinceId)
                && Objects.equals(province, that.province)
                && Objects.equals(cityId, that.cityId)
                && Objects.equals(city, that.city)
                && Objects.equals(countyId, that.countyId)
                && Objects.equals(county, that.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provinceId, province, cityId, city, countyId, county);
    }

    @Override
    public Builder toBuilder() {
        return new DefaultBuilder(this);
    }

    private static class DefaultBuilder implements Builder {

        private final DefaultProductShippingOrigin shippingOrigin;

        private DefaultBuilder(DefaultProductShippingOrigin shippingOrigin) {
            this.shippingOrigin = shippingOrigin;
        }

        @Override
        public Builder provinceId(String provinceId) {
            this.shippingOrigin.setProvinceId(provinceId);
            return this;
        }

        @Override
        public Builder province(String province) {
            this.shippingOrigin.setProvince(province);
            return this;
        }

        @Override
        public Builder cityId(String cityId) {
            this.shippingOrigin.setCityId(cityId);
            return this;
        }

        @Override
        public Builder city(String city) {
            this.shippingOrigin.setCity(city);
            return this;
        }

        @Override
        public Builder countyId(String countyId) {
            this.shippingOrigin.setCountyId(countyId);
            return this;
        }

        @Override
        public Builder county(String county) {
            this.shippingOrigin.setCounty(county);
            return this;
        }

        @Override
        public ProductShippingOrigin build() {
            return this.shippingOrigin;
        }
    }

}
