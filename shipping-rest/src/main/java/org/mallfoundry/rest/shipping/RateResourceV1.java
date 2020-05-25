package org.mallfoundry.rest.shipping;

import org.mallfoundry.shipping.Rate;
import org.mallfoundry.shipping.RateService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class RateResourceV1 {

    private final RateService rateService;

    public RateResourceV1(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/stores/{store_id}/shipping-rates/{rate_id}")
    public Optional<Rate> getRate(
            @PathVariable("store_id") String storeId,
            @PathVariable("rate_id") String rateId) {
        Assert.notNull(storeId, "Store id must not be null");
        return this.rateService.getRate(rateId);
    }
}
