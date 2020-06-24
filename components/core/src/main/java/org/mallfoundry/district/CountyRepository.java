package org.mallfoundry.district;

import java.util.List;
import java.util.Optional;

public interface CountyRepository {

    InternalCounty save(InternalCounty county);

    List<InternalCounty> findAll(DistrictQuery query);

    Optional<InternalCounty> findById(String id);
}
