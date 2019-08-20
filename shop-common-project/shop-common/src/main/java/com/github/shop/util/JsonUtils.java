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

package com.github.shop.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public abstract class JsonUtils {

    private static final ThreadLocal<ObjectMapper> currentObjectMapper = new ThreadLocal<>();

    private static ObjectMapper getObjectMapper() {

        ObjectMapper instance = currentObjectMapper.get();

        if (instance == null) {
            instance = new ObjectMapper();
            currentObjectMapper.set(instance);
        }
        return instance;
    }

    public static <T> T parse(String src, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(src, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseList(String src, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = getObjectMapper();
            return objectMapper.readValue(src, objectMapper.getTypeFactory().constructParametricType(List.class, clazz));
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
}
