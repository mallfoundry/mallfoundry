package org.mallfoundry.district;

import java.util.List;

public interface CountyRepository {

    InternalCounty save(InternalCounty county);

    int countByCityId(String cityId);

    List<InternalCounty> findAllByCityId(String cityId);
}
