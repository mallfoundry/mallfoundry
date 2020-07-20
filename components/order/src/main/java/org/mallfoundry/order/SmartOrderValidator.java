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
import lombok.Setter;
import org.mallfoundry.validation.BindRuntimeException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SmartOrderValidator implements OrderProcessor {

    private final SmartValidator validator;

    public SmartOrderValidator(SmartValidator validator) {
        this.validator = validator;
    }

    private BindingResult createError(PlaceOrders orders) {
        return new BeanPropertyBindingResult(orders, "place");
    }

    private void validate(List<Order> orders) {
        var target = new PlaceOrders(orders);
        var error = this.createError(target);
        ValidationUtils.invokeValidator(this.validator, target, error);
        if (error.hasErrors()) {
            throw new BindRuntimeException(error);
        }
    }

    @Override
    public List<Order> preProcessPlaceOrders(List<Order> orders) {
        this.validate(orders);
        return orders;
    }

    @Getter
    @Setter
    private static class PlaceOrders {

        @Valid
        @NotEmpty
        private List<Order> orders;

        PlaceOrders(List<Order> orders) {
            this.orders = orders;
        }
    }
}
