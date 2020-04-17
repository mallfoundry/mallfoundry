package com.mallfoundry.tracking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.shipping.CarrierCode;
import com.mallfoundry.shipping.Track;
import com.mallfoundry.shipping.TrackingEvent;
import com.mallfoundry.shipping.TrackingStatus;
import com.mallfoundry.tracking.repository.jpa.convert.TrackingEventListConverter;
import lombok.Getter;
import lombok.Setter;
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
@Table(name = "tracks")
@IdClass(InternalTrackId.class)
public class InternalTrack implements Track {

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

    public static InternalTrack of(Track track) {
        if (track instanceof InternalTrack) {
            return (InternalTrack) track;
        }

        var target = new InternalTrack();
        BeanUtils.copyProperties(track, target);
        return target;
    }

    public boolean isExpired() {
        return this.trackingStatus != TrackingStatus.DELIVERED &&
                this.expires < System.currentTimeMillis();
    }

}
