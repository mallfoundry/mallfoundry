package com.mallfoundry.tracking;

import com.mallfoundry.carrier.CarrierCode;

public interface TrackService {

    Track getTrack(CarrierCode carrier, String trackingNumber);
}
