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

package org.mallfoundry.trade;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BalanceSupport implements MutableBalance {

    @Override
    public BalanceSource getSource(SourceType sourceType) {
        return this.getSources().stream()
                .filter(source -> source.getType().equals(sourceType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void credit(SourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            source = this.createSource(sourceType);
            this.getSources().add(source);
        }
        source.credit(amount);
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }

    @Override
    public void debit(SourceType sourceType, BigDecimal amount) {
        var source = this.getSource(sourceType);
        if (Objects.isNull(source)) {
            throw new BalanceException(String.format("The source(%s) is not null", sourceType));
        }
        source.credit(amount);
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
    }

    @Override
    public void freeze(BigDecimal amount) {
        this.setAvailableAmount(this.getAvailableAmount().subtract(amount));
        this.setPendingAmount(this.getPendingAmount().add(amount));
    }

    @Override
    public void unfreeze(BigDecimal amount) {
        this.setPendingAmount(this.getPendingAmount().subtract(amount));
        this.setAvailableAmount(this.getAvailableAmount().add(amount));
    }
}
