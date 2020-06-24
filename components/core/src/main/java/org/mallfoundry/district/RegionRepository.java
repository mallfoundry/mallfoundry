package org.mallfoundry.district;

import java.util.List;

public interface RegionRepository {

    InternalRegion save(InternalRegion region);

    List<InternalRegion> findAllByCountryId(String countryId);
}
