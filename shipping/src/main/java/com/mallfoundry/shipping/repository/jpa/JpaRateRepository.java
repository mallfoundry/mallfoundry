package com.mallfoundry.shipping.repository.jpa;

import com.mallfoundry.shipping.InternalRate;
import com.mallfoundry.shipping.RateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRateRepository
        extends JpaRepository<InternalRate, String>, RateRepository {

}
