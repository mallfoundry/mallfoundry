package org.mallfoundry.tracking.repository.jpa;

import org.mallfoundry.tracking.InternalTracker;
import org.mallfoundry.tracking.InternalTrackerId;
import org.mallfoundry.tracking.TrackerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTrackerRepository
        extends JpaRepository<InternalTracker, InternalTrackerId>,
        TrackerRepository {
}
