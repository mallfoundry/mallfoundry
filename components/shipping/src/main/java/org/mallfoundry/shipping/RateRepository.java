package org.mallfoundry.shipping;

import java.util.Optional;

public interface RateRepository {

    InternalRate save(InternalRate rate);

    Optional<InternalRate> findById(String id);

}
