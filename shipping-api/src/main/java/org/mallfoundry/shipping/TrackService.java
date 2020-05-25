package org.mallfoundry.shipping;

public interface TrackService {

    Track getTrack(CarrierCode carrier, String trackingNumber);
}
