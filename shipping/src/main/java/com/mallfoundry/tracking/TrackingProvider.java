package com.mallfoundry.tracking;

public interface TrackingProvider {

    Tracker getTracking(String carrier, String trackingNumber);
}
