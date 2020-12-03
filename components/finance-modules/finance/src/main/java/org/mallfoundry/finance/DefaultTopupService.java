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

package org.mallfoundry.finance;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultTopupService implements TopupService, TopupProcessorInvoker {

    private List<TopupProcessor> processors;

    private final TopupRepository topupRepository;

    public DefaultTopupService(TopupRepository topupRepository) {
        this.topupRepository = topupRepository;
    }

    public void setProcessors(List<TopupProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public Topup createTopup(String topupId) {
        return this.topupRepository.create(topupId);
    }

    @Override
    public TopupQuery createTopupQuery() {
        return new DefaultTopupQuery();
    }

    @Override
    public Topup getTopup(String topupId) {
        return this.topupRepository.findById(topupId).orElseThrow();
    }

    @Override
    public SliceList<Topup> getTopups(TopupQuery query) {
        return this.topupRepository.findAll(query);
    }

    @Transactional
    @Override
    public Topup createTopup(Topup topup) throws TopupException {
        topup = this.invokePreProcessBeforeCreateTopup(topup);
        topup.create();
        topup = this.invokePreProcessAfterCreateTopup(topup);
        return this.topupRepository.save(topup);
    }

    @Override
    public PaymentNotification notifyTopup(String topupId, Object parameters) {
        return null;
    }

    private Topup requiredTopup(String topupId) throws TopupException {
        return this.topupRepository.findById(topupId).orElseThrow();
    }

    @Transactional
    @Override
    public Topup cancelTopup(String topupId) throws TopupException {
        var recharge = this.requiredTopup(topupId);
        recharge.cancel();
        return this.topupRepository.save(recharge);
    }

    @Transactional
    @Override
    public Topup succeedTopup(String topupId) throws TopupException {
        var recharge = this.requiredTopup(topupId);
        recharge.succeed();
        return this.topupRepository.save(recharge);
    }

    @Transactional
    @Override
    public Topup failTopup(String topupId, String failureReason) throws TopupException {
        var recharge = this.requiredTopup(topupId);
        recharge.fail(failureReason);
        return this.topupRepository.save(recharge);
    }

    @Override
    public Topup invokePreProcessBeforeCreateTopup(Topup topup) {
        return Processors.stream(this.processors)
                .map(TopupProcessor::preProcessBeforeCreateTopup)
                .apply(topup);
    }

    @Override
    public Topup invokePreProcessAfterCreateTopup(Topup topup) {
        return Processors.stream(this.processors)
                .map(TopupProcessor::preProcessAfterCreateTopup)
                .apply(topup);
    }
}
