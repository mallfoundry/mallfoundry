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

import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.Tracker;
import org.mallfoundry.shipping.TrackerService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InternalTrackerService implements TrackerService {
    private final TrackerRepository trackRepository;
    private final TrackerProvider trackProvider;

    public InternalTrackerService(TrackerRepository trackRepository,
                                  TrackerProvider trackProvider) {
        this.trackRepository = trackRepository;
        this.trackProvider = trackProvider;
    }

    @Override
    public Tracker getTracker(CarrierCode carrier, String trackingNumber) {
        var oldTrack = this.trackRepository.findById(InternalTrackerId.of(carrier, trackingNumber)).orElse(null);
        if (Objects.isNull(oldTrack) || oldTrack.isExpired()) {
            var track = this.trackProvider.getTracker(carrier, trackingNumber);
            var newTrack = InternalTracker.of(track);
            newTrack.setExpires(System.currentTimeMillis() + 1000 * 60 * 10); // 10 minutes
            return this.trackRepository.save(newTrack);
        }
        return oldTrack;
    }
}
