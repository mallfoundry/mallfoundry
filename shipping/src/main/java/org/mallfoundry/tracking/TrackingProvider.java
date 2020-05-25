package org.mallfoundry.tracking;

import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Tracker;

public interface TrackingProvider {

    Tracker getTracker(CarrierCode carrier, String trackingNumber);
}
