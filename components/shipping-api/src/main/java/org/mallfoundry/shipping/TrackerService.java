package org.mallfoundry.shipping;

public interface TrackerService {

    Tracker getTracker(CarrierCode carrier, String trackingNumber);
}
