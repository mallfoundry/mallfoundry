package com.mallfoundry.tracking.repository.jpa;

import com.mallfoundry.tracking.InternalTrack;
import com.mallfoundry.tracking.InternalTrackId;
import com.mallfoundry.tracking.InternalTrackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInternalTrackRepository
        extends JpaRepository<InternalTrack, InternalTrackId>,
        InternalTrackRepository {
}
