package com.mallfoundry.district;

import java.util.List;

public interface RegionRepository {

    InternalRegion save(InternalRegion region);

    List<InternalRegion> findAllByCountryId(String countryId);

    int countByCountryId(String countryId);
}
