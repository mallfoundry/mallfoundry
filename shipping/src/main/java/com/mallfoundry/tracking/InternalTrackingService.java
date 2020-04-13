package com.mallfoundry.tracking;

import com.mallfoundry.tracking.provider.KdniaoTrackingProvider;
import org.springframework.stereotype.Service;

@Service
public class InternalTrackingService implements TrackingService {

    private TrackingProvider trackingProvider = new KdniaoTrackingProvider();

    @Override
    public Tracker getTracking(String carrier, String trackingNumber) {
        return this.trackingProvider.getTracking(carrier, trackingNumber);
    }
}
