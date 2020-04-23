package com.mallfoundry.shipping;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface RateService {

    Rate createRate(String storeId);

    Zone createZone();

    Rate saveRate(Rate rate);

    void deleteRate(String rateId);

    Optional<Rate> getRate(String rateId);

    SliceList<Rate> getRates();
}
