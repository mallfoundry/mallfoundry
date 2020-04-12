package com.mallfoundry.shipping;

import java.util.Date;
import java.util.List;

public interface Tracking {

    String getTrackingNumber();

    TrackingStatus getTrackingStatus();

    Date getShippedTime();

    List<TrackingEvent> getEvents();
}
