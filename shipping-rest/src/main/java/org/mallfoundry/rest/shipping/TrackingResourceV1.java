package org.mallfoundry.rest.shipping;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Tracker;
import org.mallfoundry.shipping.TrackerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class TrackingResourceV1 {

    private final TrackerService trackingService;

    public TrackingResourceV1(TrackerService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/carriers/{carrier_code}/trackers/{tracking_number}")
    public Tracker getTracker(@PathVariable("carrier_code") String carrierCode,
                              @PathVariable("tracking_number") String trackingNumber) {
        return this.trackingService.getTracker(CarrierCode.valueOf(StringUtils.upperCase(carrierCode)), trackingNumber);
    }
}
