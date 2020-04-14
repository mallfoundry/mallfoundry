package com.mallfoundry.tracking;

import com.mallfoundry.carrier.CarrierCode;

public interface TrackProvider {

    Track getTrack(CarrierCode carrier, String trackingNumber);
}
