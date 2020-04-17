package com.mallfoundry.tracking;

import com.mallfoundry.shipping.CarrierCode;
import com.mallfoundry.shipping.Track;
import com.mallfoundry.shipping.TrackService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InternalTrackService implements TrackService {
    private final InternalTrackRepository trackRepository;
    private final TrackProvider trackProvider;

    public InternalTrackService(InternalTrackRepository trackRepository,
                                TrackProvider trackProvider) {
        this.trackRepository = trackRepository;
        this.trackProvider = trackProvider;
    }

    @Override
    public Track getTrack(CarrierCode carrier, String trackingNumber) {
        var oldTrack = this.trackRepository
                .findById(InternalTrackId.of(carrier, trackingNumber))
                .orElse(null);
        if (Objects.isNull(oldTrack) || oldTrack.isExpired()) {
            var track = this.trackProvider.getTrack(carrier, trackingNumber);
            var newTrack = InternalTrack.of(track);
            newTrack.setExpires(System.currentTimeMillis() + 1000 * 60 * 10); // 10 minutes
            return this.trackRepository.save(newTrack);
        }
        return oldTrack;
    }
}
