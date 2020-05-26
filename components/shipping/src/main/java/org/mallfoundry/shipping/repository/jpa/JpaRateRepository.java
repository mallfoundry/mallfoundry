package org.mallfoundry.shipping.repository.jpa;

import org.mallfoundry.shipping.InternalRate;
import org.mallfoundry.shipping.RateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRateRepository
        extends JpaRepository<InternalRate, String>, RateRepository {

}
