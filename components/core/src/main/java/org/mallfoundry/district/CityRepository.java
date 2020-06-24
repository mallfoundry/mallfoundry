package org.mallfoundry.district;

import java.util.List;

public interface CityRepository {

    InternalCity save(InternalCity city);

    List<InternalCity> findAllByProvinceId(String provinceId);
}
