package org.mallfoundry.tracking;

import java.util.Optional;

public interface InternalTrackRepository {

    Optional<InternalTrack> findById(InternalTrackId id);

    InternalTrack save(InternalTrack tracker);

}
