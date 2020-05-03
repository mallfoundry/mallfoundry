package com.mallfoundry.district;

import java.util.List;

public interface City extends District {

    String getProvinceId();

    void setProvinceId(String provinceId);

    List<County> getCounties();

    void setCounties(List<County> counties);
}
