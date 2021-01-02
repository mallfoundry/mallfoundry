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

import org.mallfoundry.catalog.option.Option;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Objects;

/**
 * 用于设置商品对象标识插件。如果商品对象标识不为空则不会设置一个新的标识值。
 *
 * @author Zhi Tang
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductIdentityProcessor implements ProductProcessor {

    /**
     * 商品对象标识值名称。
     */
    private static final String PRODUCT_ID_VALUE_NAME = "catalog.product.id";

    /**
     * 商品变体对象标识值名称。
     */
    private static final String PRODUCT_VARIANT_ID_VALUE_NAME = "catalog.product.variant.id";

    /**
     * 商品选项对象标识值名称。
     */
    private static final String PRODUCT_OPTION_ID_VALUE_NAME = "catalog.product.option.id";

    /**
     * 商品选项值对象标识值名称。
     */
    private static final String PRODUCT_OPTION_VALUE_ID_VALUE_NAME = "catalog.product.option.value.id";

    @Override
    public Product preProcessBeforeAddProduct(Product product) {
        this.setProduct(product);
        return product;
    }

    @Override
    public Product preProcessBeforeUpdateProduct(Product product) {
        this.setProduct(product);
        return product;
    }

    private void setProduct(Product product) {
        if (Objects.isNull(product.getId())) {
            product.setId(PrimaryKeyHolder.next(PRODUCT_ID_VALUE_NAME));
        }
        setProductOptions(product.getOptions());
        setProductVariants(product);
    }

    private void setProductOptions(List<Option> options) {
        options.stream()
                .peek(option -> {
                    if (Objects.isNull(option.getId())) {
                        option.setId(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME));
                    }
                })
                // 设置商品选项值对象的标识。
                .flatMap(option -> option.getValues().stream())
                .forEach(value -> {
                    if (Objects.isNull(value.getId())) {
                        value.setId(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME));
                    }
                });
    }

    private void setProductVariants(Product product) {
        product.getVariants().stream()
                .peek(variant -> {
                    if (Objects.isNull(variant.getId())) {
                        variant.setId(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME));
                    }
                })
                // 对商品变体对象所关联的选项对象和选项值对象设置相关联标识值。
                .flatMap(variant -> variant.getOptionSelections().stream())
                .forEach(selection -> {
                    // 如果选择的商品选项对象的名称标识或者值标识为空时，才对其执行设置标识。
                    // 如果商标变体对象所关联的商品选项对象不存在，则忽略设置。
                    if (Objects.isNull(selection.getNameId()) || Objects.isNull(selection.getValueId())) {
                        product.selectOption(selection.getName(), selection.getValue())
                                .ifPresent(aSelection -> {
                                    if (Objects.isNull(selection.getNameId())) {
                                        selection.setNameId(aSelection.getNameId());
                                    }
                                    if (Objects.isNull(selection.getValueId())) {
                                        selection.setValueId(aSelection.getValueId());
                                    }
                                });
                    }
                });
    }
}
