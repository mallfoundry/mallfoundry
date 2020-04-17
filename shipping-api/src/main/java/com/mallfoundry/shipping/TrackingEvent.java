package com.mallfoundry.shipping;

import java.util.Date;

public interface TrackingEvent extends Comparable<TrackingEvent> {

    TrackingStatus getStatus();

    String getMessage();

    Date getOccurredTime();
}
