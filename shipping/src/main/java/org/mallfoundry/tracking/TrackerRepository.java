package org.mallfoundry.tracking;

import java.util.Optional;

public interface TrackerRepository {

    Optional<InternalTracker> findById(InternalTrackerId id);

    InternalTracker save(InternalTracker tracker);

}
