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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.util.Positions;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductOptionSupport implements MutableProductOption {

    public ProductOptionSupport(String id) {
        this.setId(id);
    }

    @Override
    public ProductOptionValue createValue(String valueId) {
        return new DefaultProductOptionValue(valueId);
    }

    @Override
    public Optional<ProductOptionValue> getValue(String label) {
        return this.getValues().stream().filter(value -> Objects.equals(value.getLabel(), label)).findFirst();
    }

    @Override
    public void addValue(ProductOptionValue value) {
        this.getValues().add(value);
        Positions.sort(this.getValues());
    }

    @Override
    public void removeValue(ProductOptionValue value) {
        this.getValues().remove(value);
    }
}
