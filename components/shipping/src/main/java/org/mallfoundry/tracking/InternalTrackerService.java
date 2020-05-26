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
