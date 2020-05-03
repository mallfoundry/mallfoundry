package com.mallfoundry.district;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalDistrictQuery implements DistrictQuery {
    private String countryId;
    private byte scope;

    @Override
    public DistrictQueryBuilder toBuilder() {
        return new DistrictQueryBuilder(this);
    }
}
