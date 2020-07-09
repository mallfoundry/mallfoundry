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

import com.fasterxml.jackson.annotation.JsonFormat;
import org.mallfoundry.shipping.TrackingEvent;
import org.mallfoundry.shipping.TrackingStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InternalTrackingEvent implements TrackingEvent {
    private TrackingStatus status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date occurredTime;

    @Override
    public TrackingStatus getStatus() {
        return status;
    }

    public void setStatus(TrackingStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Date getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(Date occurredTime) {
        this.occurredTime = occurredTime;
    }

    @Override
    public int compareTo(TrackingEvent o) {
        return this.getOccurredTime().compareTo(o.getOccurredTime());
    }
}
