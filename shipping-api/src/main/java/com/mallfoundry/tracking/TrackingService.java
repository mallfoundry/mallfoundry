package com.mallfoundry.tracking;

public interface TrackingService {

    Tracker getTracking(String carrier, String trackingNumber);
}
