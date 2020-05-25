package org.mallfoundry.tracking;

import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Track;

public interface TrackProvider {

    Track getTrack(CarrierCode carrier, String trackingNumber);
}
