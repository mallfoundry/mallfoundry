package com.mallfoundry.tracking;

import com.mallfoundry.carrier.CarrierCode;

import java.util.Date;
import java.util.List;

public interface Tracker {

    CarrierCode getCarrierCode();

    String getTrackingNumber();

    TrackingStatus getTrackingStatus();

    Date getShippedTime();

    List<TrackingEvent> getEvents();
}
