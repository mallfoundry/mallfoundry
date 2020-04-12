package com.mallfoundry.tracking;

import java.util.Date;

public interface TrackingEvent {

    TrackingStatus getStatus();

    String getMessage();

    Date getOccurredTime();
}
