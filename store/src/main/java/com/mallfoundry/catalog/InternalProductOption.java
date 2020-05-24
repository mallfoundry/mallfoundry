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

package com.mallfoundry.catalog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mallfoundry.catalog.repository.jpa.convert.ProductOptionValueListConverter;
import com.mallfoundry.util.Positions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalProductOption implements ProductOption {

    @Column(name = "name_")
    private String name;

    @Column(name = "values_", length = 1024)
    @Convert(converter = ProductOptionValueListConverter.class)
    @JsonDeserialize(contentAs = InternalProductOptionValue.class)
    private List<ProductOptionValue> values = new ArrayList<>();

    @Column(name = "position_")
    private Integer position;

    public InternalProductOption(String name) {
        this.name = name;
    }

    @Override
    public ProductOptionValue createValue(String label) {
        return new InternalProductOptionValue(label);
    }

    @Override
    public Optional<ProductOptionValue> getValue(String label) {
        return this.values.stream().filter(value -> Objects.equals(value.getLabel(), label)).findFirst();
    }

    @Override
    public void addValue(ProductOptionValue value) {
        this.values.add(value);
        Positions.sort(this.values);
    }

    @Override
    public void removeValue(ProductOptionValue value) {
        this.values.remove(value);
    }
}
