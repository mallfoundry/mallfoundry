package com.mallfoundry.tracking;

import com.mallfoundry.shipping.CarrierCode;
import com.mallfoundry.shipping.Track;

public interface TrackProvider {

    Track getTrack(CarrierCode carrier, String trackingNumber);
}
