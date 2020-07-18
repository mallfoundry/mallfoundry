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

package org.mallfoundry.catalog.product;

import org.mallfoundry.validation.BindRuntimeException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;

/**
 * 使用 Spring SmartValidator 校验商品对象。
 *
 * @author Zhi Tang
 */
public class SmartProductValidator implements ProductProcessor {

    private final SmartValidator validator;

    public SmartProductValidator(SmartValidator validator) {
        this.validator = validator;
    }

    @Override
    public Product preProcessAddProduct(Product product) {
        this.validate(product);
        return product;
    }

    @Override
    public Product preProcessUpdateProduct(Product product) {
        this.validate(product);
        return product;
    }

    private BindingResult createError(Product product) {
        return new BeanPropertyBindingResult(product, "product");
    }

    private void validate(Product product) {
        var error = this.createError(product);
        ValidationUtils.invokeValidator(this.validator, product, error);
        if (error.hasErrors()) {
            throw new BindRuntimeException(error);
        }
    }
}
