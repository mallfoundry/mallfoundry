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

package com.github.shop.spring.boot.autoconfigure.storage;

import com.github.shop.storage.StorageConfiguration;
import com.github.shop.storage.StorageSystem;
import com.github.shop.storage.StorageSystems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableConfigurationProperties(ShopStorageProperties.class)
public class ShopStorageAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnClass(StorageSystem.class)
    public StorageSystem storageSystem(ShopStorageProperties properties) {
        return StorageSystems.newStorageSystem(this.getConfiguration(properties));
    }

    private StorageConfiguration getConfiguration(ShopStorageProperties properties) {
        StorageConfiguration config = new StorageConfiguration();
        config.getHttp().setBaseUrl(properties.getHttp().getBaseUrl());
        config.getStore().setDirectory(properties.getStore().getDirectory());
        config.getStore().setType(toStoreType(properties.getStore().getType()));
        return config;
    }

    private StorageConfiguration.StoreType toStoreType(ShopStorageProperties.StoreType storeType) {
        switch (storeType) {
            case LOCAL:
                return StorageConfiguration.StoreType.LOCAL;
            case FTP:
                return StorageConfiguration.StoreType.FTP;
            case ALI_CLOUD_OOS:
                return StorageConfiguration.StoreType.ALI_CLOUD_OOS;
            default:
                return null;
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "shop.storage.store", name = "type", havingValue = "local")
    public static class ResourceHandlerConfiguration implements WebMvcConfigurer {

        @Autowired
        private ShopStorageProperties properties;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("file:" + properties.getStore().getDirectory());
        }
    }
}
