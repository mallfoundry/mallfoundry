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
import org.mallfoundry.shipping.Track;
import org.mallfoundry.shipping.TrackService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultTrackService implements TrackService {
    private final TrackRepository trackRepository;
    private final TrackProvider trackProvider;

    public DefaultTrackService(TrackRepository trackRepository,
                               TrackProvider trackProvider) {
        this.trackRepository = trackRepository;
        this.trackProvider = trackProvider;
    }

    @Override
    public Track getTrack(CarrierCode carrier, String trackingNumber) {
        var oldTrack = this.trackRepository.findById(InternalTrackId.of(carrier, trackingNumber)).orElse(null);
        if (Objects.isNull(oldTrack) || oldTrack.isExpired()) {
            var track = this.trackProvider.getTracker(carrier, trackingNumber);
            var newTrack = InternalTrack.of(track);
            newTrack.setExpires(System.currentTimeMillis() + 1000 * 60 * 10); // 10 minutes
            return this.trackRepository.save(newTrack);
        }
        return oldTrack;
    }
}
