package com.mallfoundry.tracking.rest;

import com.mallfoundry.tracking.Tracker;
import com.mallfoundry.tracking.TrackingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class TrackingResourceV1 {

    private final TrackingService trackingService;

    public TrackingResourceV1(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/tracks/{carrier_code}/{tracking_number}")
    public Tracker getTracking(@PathVariable("carrier_code") String carrierCode,
                               @PathVariable("tracking_number") String trackingNumber) {
        return this.trackingService.getTracking(carrierCode, trackingNumber);
    }
}
