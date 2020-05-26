package org.mallfoundry.shipping;

import java.io.Serializable;
import java.util.List;

public interface Tracker extends Serializable {

    CarrierCode getCarrierCode();

    String getTrackingNumber();

    TrackingStatus getTrackingStatus();

    List<TrackingEvent> getEvents();
}