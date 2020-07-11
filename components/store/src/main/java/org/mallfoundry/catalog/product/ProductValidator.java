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

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class ProductValidator implements ProductProcessor {

    @Override
    public Product processPreAddProduct(Product product) {
        this.validate(product);
        return product;
    }

    private void validate(Product product) {
        Assert.isTrue(isNotBlank(product.getId()), ProductMessages.notEmpty("id"));
        Assert.isTrue(isNotBlank(product.getName()), ProductMessages.notEmpty("name"));
        Assert.notNull(product.getStatus(), ProductMessages.notEmpty("status"));
        this.validateOptions(product);
        this.validateVariants(product);
    }

    private void validateOptions(Product product) {
        Assert.isTrue(CollectionUtils.isNotEmpty(product.getOptions()), ProductMessages.notEmpty("options"));
    }

    private void validateVariants(Product product) {
        Assert.notEmpty(product.getVariants(), ProductMessages.notEmpty("variants"));
        product.getVariants().stream()
                .peek(this::validateVariant)
                // 校验商品变体的规格选项集
                .forEach(variant -> variant.getOptionSelections()
                        .forEach(selection ->
                                product.selectOption(selection.getName(), selection.getValue())
                                        .orElseThrow()));
    }

    private void validateVariant(ProductVariant variant) {
        Assert.isTrue(isNotBlank(variant.getId()), ProductMessages.variantNotEmpty("id"));
        Assert.isTrue(isNotBlank(variant.getStoreId()), ProductMessages.variantNotEmpty("storeId"));
        if (Objects.isNull(variant.getSalePrice())) {
            Assert.notNull(variant.getPrice(), ProductMessages.variantNotEmpty("price"));
        }
    }
}
