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

package org.mallfoundry.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public abstract class AbstractMapPaymentNotification implements PaymentNotification {

    protected final Map<String, String> parameters;

    protected PaymentStatus status;

    protected byte[] result;

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public void pending() {
        this.setStatus(PaymentStatus.PENDING);
    }

    @Override
    public void capture() {
        this.setStatus(PaymentStatus.CAPTURED);
    }

    @Override
    public boolean isPending() {
        return PaymentStatus.PENDING == this.status;
    }

    @Override
    public boolean isCaptured() {
        return PaymentStatus.CAPTURED == this.status;
    }

    @Override
    public boolean hasResult() {
        return Objects.nonNull(this.result);
    }

    public AbstractMapPaymentNotification(Map<String, String> parameters) throws PaymentException {
        this.parameters = parameters;
    }
}
