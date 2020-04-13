package com.mallfoundry.tracking;

import java.util.Optional;

public interface InternalTrackerRepository {

    Optional<InternalTracker> findById(InternalTrackerId id);

    InternalTracker save(InternalTracker tracker);

}
