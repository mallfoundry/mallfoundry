package com.mallfoundry.tracking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mallfoundry.carrier.CarrierCode;

import java.util.Date;
import java.util.List;

public class InternalTracker implements Tracker {

    private CarrierCode carrierCode;

    private String trackingNumber;

    private TrackingStatus trackingStatus;

    private Date shippedTime;

    private List<TrackingEvent> events;

    @JsonIgnore
    private long updatedTimeMillis;

    @Override
    public CarrierCode getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(CarrierCode carrierCode) {
        this.carrierCode = carrierCode;
    }

    @Override
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public TrackingStatus getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(TrackingStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    @Override
    public Date getShippedTime() {
        return shippedTime;
    }

    public void setShippedTime(Date shippedTime) {
        this.shippedTime = shippedTime;
    }

    @Override
    public List<TrackingEvent> getEvents() {
        return events;
    }

    public void setEvents(List<TrackingEvent> events) {
        this.events = events;
    }

    public long getUpdatedTimeMillis() {
        return updatedTimeMillis;
    }

    public void setUpdatedTimeMillis(long updatedTimeMillis) {
        this.updatedTimeMillis = updatedTimeMillis;
    }

    public boolean isExpired(long interval) {
        return System.currentTimeMillis() - interval < this.updatedTimeMillis;
    }
}
