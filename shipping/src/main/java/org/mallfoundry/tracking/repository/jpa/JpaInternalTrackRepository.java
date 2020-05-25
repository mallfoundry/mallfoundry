package org.mallfoundry.tracking.repository.jpa;

import org.mallfoundry.tracking.InternalTrack;
import org.mallfoundry.tracking.InternalTrackId;
import org.mallfoundry.tracking.InternalTrackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInternalTrackRepository
        extends JpaRepository<InternalTrack, InternalTrackId>,
        InternalTrackRepository {
}
