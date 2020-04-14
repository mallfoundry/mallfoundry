package com.mallfoundry.tracking.rest;

import com.mallfoundry.carrier.CarrierCode;
import com.mallfoundry.tracking.Track;
import com.mallfoundry.tracking.TrackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class TrackingResourceV1 {

    private final TrackService trackingService;

    public TrackingResourceV1(TrackService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/tracks/{carrier_code}/{tracking_number}")
    public Track getTracking(@PathVariable("carrier_code") String carrierCode,
                             @PathVariable("tracking_number") String trackingNumber) {
        return this.trackingService.getTrack(CarrierCode.valueOf(StringUtils.upperCase(carrierCode)), trackingNumber);
    }
}
