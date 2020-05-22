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

package com.mallfoundry.rest.store.product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InternalProductOption implements ProductOption {

    private String name;

    @JsonDeserialize(contentAs = InternalProductOptionValue.class)
    private List<ProductOptionValue> values = new ArrayList<>();

    private Integer position;

    public InternalProductOption(String name) {
        this.setName(name);
    }

    public void addSimpleValues(List<String> values) {
        List<ProductOptionValue> optionValues = new ArrayList<>();
        for (String value : values) {
            optionValues.add(new InternalProductOptionValue(value, optionValues.size()));
        }
        this.setValues(optionValues);
    }

    public void addSimpleValues(String... values) {
        this.addSimpleValues(Arrays.asList(values));
    }
}
