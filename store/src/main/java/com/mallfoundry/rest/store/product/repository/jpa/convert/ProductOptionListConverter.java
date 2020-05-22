/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.rest.store.product.repository.jpa.convert;

import com.mallfoundry.rest.store.product.InternalProductOption;
import com.mallfoundry.util.JsonUtils;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;

public class ProductOptionListConverter implements AttributeConverter<List<InternalProductOption>, String> {

    @Override
    public String convertToDatabaseColumn(List<InternalProductOption> options) {
        return Objects.isNull(options) ? null : JsonUtils.stringify(options);
    }

    @Override
    public List<InternalProductOption> convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : JsonUtils.parse(dbData, List.class, InternalProductOption.class);
    }
}
