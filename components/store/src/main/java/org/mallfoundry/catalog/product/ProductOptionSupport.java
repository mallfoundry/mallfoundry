/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
