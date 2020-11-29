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

package org.mallfoundry.finance.bank;

import lombok.Setter;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.util.Copies;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultBankCardService implements BankCardService, BankCardProcessorInvoker {

    @Setter
    private List<BankCardProcessor> processors;

    private final BankCardRepository bankCardRepository;

    public DefaultBankCardService(BankCardRepository bankCardRepository) {
        this.bankCardRepository = bankCardRepository;
    }

    @Override
    public BankCardQuery createBankCardQuery() {
        return new DefaultBankCardQuery();
    }

    @Override
    public BankCard createBankCard(String id) {
        return this.bankCardRepository.create(id);
    }

    private BankCard requiredBankCard(String id) {
        return this.bankCardRepository.findById(id)
                .orElseThrow(() -> new BankCardException(BankCardMessages.notFound()));
    }

    @Transactional
    @Override
    public BankCard bindBankCard(BankCard bankCard) {
        bankCard = this.invokePreProcessBeforeBindBankCard(bankCard);
        bankCard.bind();
        bankCard = this.invokePreProcessAfterBindBankCard(bankCard);
        return this.bankCardRepository.save(bankCard);
    }

    @Override
    public BankCard getBankCard(String id) {
        return this.requiredBankCard(id);
    }

    @Override
    public SliceList<BankCard> getBankCards(BankCardQuery query) {
        return this.bankCardRepository.findAll(query);
    }

    @Transactional
    @Override
    public BankCard updateBankCard(BankCard source) throws BankCardException {
        var bankCard = this.requiredBankCard(source.getId());
        Copies.notBlank(source::getBankName).trim(bankCard::setBankName)
                .notBlank(source::getBranchName).trim(bankCard::setBranchName);
        return this.bankCardRepository.save(bankCard);
    }

    @Transactional
    @Override
    public void unbindBankCard(String id) {
        var bankCard = this.requiredBankCard(id);
        this.bankCardRepository.delete(bankCard);
    }

    @Override
    public BankCard invokePreProcessBeforeBindBankCard(BankCard bankCard) {
        return Processors.stream(this.processors)
                .map(BankCardProcessor::preProcessBeforeBindBankCard)
                .apply(bankCard);
    }

    @Override
    public BankCard invokePreProcessAfterBindBankCard(BankCard bankCard) {
        return Processors.stream(this.processors)
                .map(BankCardProcessor::preProcessAfterBindBankCard)
                .apply(bankCard);
    }
}
