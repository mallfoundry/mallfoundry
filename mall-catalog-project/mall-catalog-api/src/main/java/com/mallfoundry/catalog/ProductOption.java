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

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ProductOption {

    private String name;

    private List<ProductOptionValue> values;

    private short index;

    public static class Builder {

        private ProductOption option;

        public Builder() {
            this.option = new ProductOption();
        }

        public Builder name(String name) {
            this.option.setName(name);
            return this;
        }

        public Builder index(short index) {
            this.option.setIndex(index);
            return this;
        }

        public Builder index(int index) {
            this.option.setIndex((short) index);
            return this;
        }

        public Builder simpleValues(List<String> values) {
            List<ProductOptionValue> optionValues = new ArrayList<>();
            for (String value : values) {
                optionValues.add(new ProductOptionValue(value, (short) optionValues.size()));
            }
            this.option.setValues(optionValues);
            return this;
        }

        public Builder simpleValues(String... values) {
            return this.simpleValues(Arrays.asList(values));
        }

        public ProductOption build() {
            return this.option;
        }
    }
}
