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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

public class DefaultRateService implements RateService {

    private final RateProcessorsInvoker processorsInvoker;

    private final RateRepository rateRepository;

    public DefaultRateService(RateProcessorsInvoker processorsInvoker, RateRepository rateRepository) {
        this.processorsInvoker = processorsInvoker;
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
        return Function.<Rate>identity()
                .compose(this.rateRepository::save)
                .compose(this.processorsInvoker::invokePreProcessAddRate)
                .apply(rate);
    }

    @Transactional
    @Override
    public Rate updateRate(Rate rate) {
        return Function.<Rate>identity()
                .compose(this.rateRepository::save)
                .compose(this.processorsInvoker::invokePreProcessUpdateRate)
                .apply(rate);
    }

    @Override
    public Optional<Rate> getRate(String rateId) {
        return this.rateRepository.findById(rateId)
                .map(this.processorsInvoker::invokePostProcessGetRate);
    }

    @Override
    public SliceList<Rate> getRates(RateQuery query) {
        return Function.<SliceList<Rate>>identity()
                .compose(this.rateRepository::findAll)
                .compose(this.processorsInvoker::invokePreProcessGetRates)
                .apply(query);
    }

    private Rate requiredRate(String rateId) {
        return this.rateRepository.findById(rateId).orElseThrow(RateExceptions::notFound);
    }

    @Transactional
    @Override
    public void deleteRate(String rateId) {
        var rate = Function.<Rate>identity()
                .compose(this.processorsInvoker::invokePreProcessDeleteRate)
                .compose(this::requiredRate)
                .apply(rateId);
        this.rateRepository.delete(rate);
    }
}
