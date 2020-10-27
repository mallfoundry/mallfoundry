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

package org.mallfoundry.finance.account;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BalanceSupport implements MutableBalance {

    @Override
    public BalanceSource getSource(BalanceSourceType sourceType) {
        return this.getSources().stream()
                .filter(source -> source.getType().equals(sourceType))
                .findFirst()
                .orElse(null);
    }

    private void creditSource(BalanceSourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            source = this.createSource(sourceType);
            this.getSources().add(source);
        }
        source.credit(amount);
    }

    private void debitSource(BalanceSourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            throw new BalanceException(String.format("The source(%s) is not null", sourceType));
        }
        source.debit(amount);
    }

    @Override
    public void pending(BalanceSourceType sourceType, BigDecimal amount) throws BalanceException {
        this.creditSource(sourceType, amount);
        this.setPendingAmount(this.getPendingAmount().add(amount));
    }

    @Override
    public void refund(BalanceSourceType sourceType, BigDecimal amount) throws BalanceException {
        this.debitSource(sourceType, amount);
        this.setPendingAmount(this.getPendingAmount().subtract(amount));
    }

    @Override
    public void settle(BigDecimal amount) throws BalanceException {
        this.setPendingAmount(this.getPendingAmount().subtract(amount));
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }

    @Override
    public void credit(BalanceSourceType sourceType, BigDecimal amount) {
        this.creditSource(sourceType, amount);
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }

    @Override
    public void debit(BalanceSourceType sourceType, BigDecimal amount) {
        this.debitSource(sourceType, amount);
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
    }

    @Override
    public void freeze(BigDecimal amount) {
        this.setFreezeAmount(this.getFreezeAmount().add(amount));
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
    }

    @Override
    public void unfreeze(BigDecimal amount) {
        this.setFreezeAmount(this.getFreezeAmount().subtract(amount));
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }
}
