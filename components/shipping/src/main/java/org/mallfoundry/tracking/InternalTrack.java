/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.tracking;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Track;
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
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mf_shipping_track")
@IdClass(InternalTrackId.class)
public class InternalTrack implements Track {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "carrier_code_")
    private CarrierCode carrierCode;

    @Id
    @Column(name = "tracking_number_")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_status_")
    private TrackingStatus trackingStatus;

    @Convert(converter = TrackingEventListConverter.class)
    @Column(name = "events_", length = 1024 * 8)
    private List<TrackingEvent> events;

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
        return this.trackingStatus != TrackingStatus.DELIVERED
                && this.expires < System.currentTimeMillis();
    }

}
