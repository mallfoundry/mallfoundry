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

package org.mallfoundry.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.finance.PaymentMethodType;
import org.mallfoundry.finance.PaymentStatus;
import org.springframework.beans.BeanUtils;

import static org.mallfoundry.finance.PaymentStatus.CAPTURED;
import static org.mallfoundry.finance.PaymentStatus.PENDING;

@Getter
@Setter
@NoArgsConstructor
public class DefaultOrderPaymentResult implements OrderPaymentResult {
    private String id;
    private PaymentMethodType method;
    private PaymentStatus status;

    public DefaultOrderPaymentResult(String id, PaymentMethodType method, PaymentStatus status) {
        this.id = id;
        this.method = method;
        this.status = status;
    }

    public static DefaultOrderPaymentResult of(OrderPaymentResult details) {
        if (details instanceof DefaultOrderPaymentResult) {
            return (DefaultOrderPaymentResult) details;
        }
        var target = new DefaultOrderPaymentResult();
        BeanUtils.copyProperties(details, target);
        return target;
    }

    @Override
    public boolean isPending() {
        return PENDING == this.status;
    }

    @Override
    public boolean isCaptured() {
        return CAPTURED == this.status;
    }
}
