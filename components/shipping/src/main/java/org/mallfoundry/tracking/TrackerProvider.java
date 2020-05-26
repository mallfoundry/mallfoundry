package org.mallfoundry.tracking;

import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Tracker;

public interface TrackerProvider {

    Tracker getTracker(CarrierCode carrier, String trackingNumber);
}
