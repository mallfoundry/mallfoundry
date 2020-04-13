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

package com.mallfoundry.spring.boot.autoconfigure.storage;

import com.mallfoundry.storage.LocalStorageSystem;
import com.mallfoundry.storage.StorageSystem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;


@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnClass(LocalStorageSystem.class)
    @ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "local")
    public StorageSystem storageSystem(StorageProperties properties) {
        return new LocalStorageSystem(properties.getDirectory(), properties.getBaseUrl());
    }

    @Configuration
    @ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "local")
    public static class ResourceHandlerConfiguration implements WebMvcConfigurer {

        private final StorageProperties properties;

        public ResourceHandlerConfiguration(StorageProperties properties) {
            this.properties = properties;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("file:" + properties.getDirectory())
                    .setCachePeriod(3000)
                    .resourceChain(true)
                    .addResolver(new EncodedResourceResolver());
        }
    }
}
