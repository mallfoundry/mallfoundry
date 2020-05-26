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

package org.mallfoundry.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class JsonUtils {

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
            objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            var timeZone = TimeZone.getTimeZone("GMT+8");
            objectMapper.setTimeZone(timeZone);
            var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(timeZone);
            objectMapper.setDateFormat(dateFormat);
        }
        return objectMapper;
    }

    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String content, Class<?> parametrized, Class<?>... parameterClasses) {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return objectMapper.readValue(content, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String stringify(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Configuration
    static class JsonUtilsConfiguration implements ApplicationContextAware {
        @Override
        public void setApplicationContext(ApplicationContext context) throws BeansException {
            objectMapper = context.getBean(ObjectMapper.class);
        }
    }
}
