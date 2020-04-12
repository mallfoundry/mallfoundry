package com.mallfoundry.tracking;

import java.util.Date;
import java.util.List;

public interface Tracker {

    String getCarrierCode();

    String getTrackingNumber();

    TrackingStatus getTrackingStatus();

    Date getShippedTime();

    List<TrackingEvent> getEvents();
}
