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

package org.mallfoundry.data.jpa.convert;

import org.mallfoundry.util.JsonUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LongListConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return Objects.isNull(attribute) ? null : JsonUtils.stringify(attribute);
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return StringUtils.isEmpty(dbData) ? new ArrayList<>() : JsonUtils.parse(dbData, List.class, Long.class);
    }
}