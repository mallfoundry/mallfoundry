/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.shipping.rate;

import org.mallfoundry.data.SliceList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultRateService implements RateService {

//    private static final String RATE_ID_VALUE_NAME = "shipping.rate.id";
//    private static final String ZONE_ID_VALUE_NAME = "shipping.zone.id";

    private final RateRepository rateRepository;

    public DefaultRateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public RateQuery createRateQuery() {
        return new DefaultRateQuery();
    }

    @Override
    public Rate createRate(String rateId) {
        return this.rateRepository.create(rateId);
    }

    @Transactional
    @Override
    public Rate addRate(Rate rate) {
        return this.rateRepository.save(rate);
    }

    @Override
    public Rate updateRate(Rate rate) {
        return this.rateRepository.save(rate);
    }

    @Override
    public Optional<Rate> getRate(String rateId) {
        return this.rateRepository.findById(rateId);
    }

    @Override
    public SliceList<Rate> getRates(RateQuery query) {
        return this.rateRepository.findAll(query);
    }

    private Rate requiredRate(String rateId) {
        return this.rateRepository.findById(rateId).orElseThrow(RateExceptions::notFound);
    }

    @Transactional
    @Override
    public void deleteRate(String rateId) {
        var rate = this.requiredRate(rateId);
        this.rateRepository.delete(rate);
    }
}
