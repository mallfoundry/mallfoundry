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

public class DefaultRechargeService implements RechargeService, RechargeProcessorInvoker {

    private List<RechargeProcessor> processors;

    private final RechargeRepository rechargeRepository;

    public DefaultRechargeService(RechargeRepository rechargeRepository) {
        this.rechargeRepository = rechargeRepository;
    }

    public void setProcessors(List<RechargeProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public Recharge createRecharge(String rechargeId) {
        return this.rechargeRepository.create(rechargeId);
    }

    @Override
    public RechargeQuery createRechargeQuery() {
        return new DefaultRechargeQuery();
    }

    @Override
    public Recharge getRecharge(String rechargeId) {
        return this.rechargeRepository.findById(rechargeId).orElseThrow();
    }

    @Override
    public SliceList<Recharge> getRecharges(RechargeQuery query) {
        return this.rechargeRepository.findAll(query);
    }

    @Transactional
    @Override
    public Recharge createRecharge(Recharge recharge) throws RechargeException {
        recharge = this.invokePreProcessBeforeCreateRecharge(recharge);
        recharge.create();
        recharge = this.invokePreProcessAfterCreateRecharge(recharge);
        return this.rechargeRepository.save(recharge);
    }

    private Recharge requiredRecharge(String rechargeId) throws RechargeException {
        return this.rechargeRepository.findById(rechargeId).orElseThrow();
    }

    @Transactional
    @Override
    public Recharge cancelRecharge(String rechargeId) throws RechargeException {
        var recharge = this.requiredRecharge(rechargeId);
        recharge.cancel();
        return this.rechargeRepository.save(recharge);
    }

    @Transactional
    @Override
    public Recharge succeedRecharge(String rechargeId) throws RechargeException {
        var recharge = this.requiredRecharge(rechargeId);
        recharge.succeed();
        return this.rechargeRepository.save(recharge);
    }

    @Transactional
    @Override
    public Recharge failRecharge(String rechargeId, String failureReason) throws RechargeException {
        var recharge = this.requiredRecharge(rechargeId);
        recharge.fail(failureReason);
        return this.rechargeRepository.save(recharge);
    }

    @Override
    public Recharge invokePreProcessBeforeCreateRecharge(Recharge recharge) {
        return Processors.stream(this.processors)
                .map(RechargeProcessor::preProcessBeforeCreateRecharge)
                .apply(recharge);
    }

    @Override
    public Recharge invokePreProcessAfterCreateRecharge(Recharge recharge) {
        return Processors.stream(this.processors)
                .map(RechargeProcessor::preProcessAfterCreateRecharge)
                .apply(recharge);
    }
}
