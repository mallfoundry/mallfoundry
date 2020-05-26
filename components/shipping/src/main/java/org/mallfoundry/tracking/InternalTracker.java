package org.mallfoundry.tracking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Tracker;
import org.mallfoundry.shipping.TrackingEvent;
import org.mallfoundry.shipping.TrackingStatus;
import org.mallfoundry.tracking.repository.jpa.convert.TrackingEventListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shipping_trackers")
@IdClass(InternalTrackerId.class)
public class InternalTracker implements Tracker {

    @JsonProperty("carrier_code")
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "carrier_code_")
    private CarrierCode carrierCode;

    @JsonProperty("tracking_number")
    @Id
    @Column(name = "tracking_number_")
    private String trackingNumber;

    @JsonProperty("tracking_status")
    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_status_")
    private TrackingStatus trackingStatus;

    @Lob
    @Convert(converter = TrackingEventListConverter.class)
    @Column(name = "events_")
    private List<TrackingEvent> events;

    @JsonIgnore
    @Column(name = "expires_")
    private long expires;

    public static InternalTracker of(Tracker track) {
        if (track instanceof InternalTracker) {
            return (InternalTracker) track;
        }

        var target = new InternalTracker();
        BeanUtils.copyProperties(track, target);
        return target;
    }

    public boolean isExpired() {
        return this.trackingStatus != TrackingStatus.DELIVERED
                && this.expires < System.currentTimeMillis();
    }

}
