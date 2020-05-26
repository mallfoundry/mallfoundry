package org.mallfoundry.shipping;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalRateService implements RateService {

    private static final String RATE_ID_VALUE_NAME = "shipping.rate.id";

    private static final String ZONE_ID_VALUE_NAME = "shipping.zone.id";

    private final RateRepository rateRepository;

    public InternalRateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Rate createRate(String storeId) {
        return new InternalRate(PrimaryKeyHolder.next(RATE_ID_VALUE_NAME), storeId);
    }

    @Override
    public Zone createZone() {
        return new InternalZone(PrimaryKeyHolder.next(ZONE_ID_VALUE_NAME));
    }

    @Override
    public Rate saveRate(Rate rate) {
        return this.rateRepository.save(InternalRate.of(rate));
    }

    @Override
    public void deleteRate(String rateId) {

    }

    @Override
    public Optional<Rate> getRate(String rateId) {
        return CastUtils.cast(this.rateRepository.findById(rateId));
    }

    @Override
    public SliceList<Rate> getRates() {
        return null;
    }
}
