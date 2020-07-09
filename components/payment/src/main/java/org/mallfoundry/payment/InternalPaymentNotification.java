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
import org.springframework.data.util.CastUtils;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class InternalPaymentNotification implements PaymentNotification {

    private Map<String, String> parameters;

    private Map<String, String[]> parameterMap;

    private PaymentStatus status;

    private byte[] result;

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

    public InternalPaymentNotification(Object parameterObject) throws PaymentException {
        if (parameterObject instanceof Map) {
            this.parameterMap = CastUtils.cast(parameterObject);
            Map<String, ?> parameterMap = CastUtils.cast(parameterObject);
            this.parameters =
                    parameterMap.entrySet().stream().map(entry -> {
                        if (entry.getValue() instanceof String[]) {
                            String[] values = (String[]) entry.getValue();
                            return Map.entry(entry.getKey(), values[0]);
                        } else {
                            return Map.entry(entry.getKey(), (String) entry.getValue());
                        }
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            throw new PaymentException("");
        }
    }
}
