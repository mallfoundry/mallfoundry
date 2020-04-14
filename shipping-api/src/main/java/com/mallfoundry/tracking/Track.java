package com.mallfoundry.tracking;

import com.mallfoundry.carrier.CarrierCode;

import java.io.Serializable;
import java.util.List;

public interface Track extends Serializable {

    CarrierCode getCarrierCode();

    String getTrackingNumber();

    TrackingStatus getTrackingStatus();

    List<TrackingEvent> getEvents();
}
