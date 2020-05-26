package org.mallfoundry.district;

import java.util.List;

public interface CityRepository {

    InternalCity save(InternalCity city);

    int countByProvinceId(String provinceId);

    List<InternalCity> findAllByProvinceId(String provinceId);
}
