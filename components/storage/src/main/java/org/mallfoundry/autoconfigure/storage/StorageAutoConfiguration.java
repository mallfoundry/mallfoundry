/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.autoconfigure.storage;

import org.mallfoundry.storage.BlobRepository;
import org.mallfoundry.storage.BucketRepository;
import org.mallfoundry.storage.DefaultStorageService;
import org.mallfoundry.storage.LocalStorageSystem;
import org.mallfoundry.storage.SharedBlobRepository;
import org.mallfoundry.storage.StorageIdentityProcessor;
import org.mallfoundry.storage.StoragePathReplacer;
import org.mallfoundry.storage.StorageProcessor;
import org.mallfoundry.storage.StorageSystem;
import org.mallfoundry.storage.aliyun.AliyunStorageSystem;
import org.mallfoundry.storage.qiniu.QiniuStorageSystem;
import org.mallfoundry.storage.repository.jpa.DelegatingJpaBlobRepository;
import org.mallfoundry.storage.repository.jpa.DelegatingJpaBucketRepository;
import org.mallfoundry.storage.repository.jpa.JpaBlobRepository;
import org.mallfoundry.storage.repository.jpa.JpaBucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

import java.util.List;

@Configuration
@Import(StorageFtpConfiguration.class)
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public DelegatingJpaBucketRepository delegatingJpaBucketRepository(JpaBucketRepository repository) {
        return new DelegatingJpaBucketRepository(repository);
    }

    @Bean
    public DelegatingJpaBlobRepository delegatingJpaBlobRepository(JpaBlobRepository repository) {
        return new DelegatingJpaBlobRepository(repository);
    }

    @Bean
    public StorageIdentityProcessor storageIdentityProcessor() {
        return new StorageIdentityProcessor();
    }

    @Bean
    public DefaultStorageService defaultStorageService(@Lazy List<StorageProcessor> processors,
                                                       StorageSystem storageSystem,
                                                       BucketRepository bucketRepository,
                                                       BlobRepository blobRepository,
                                                       SharedBlobRepository sharedBlobRepository) {
        var service = new DefaultStorageService(storageSystem, bucketRepository, blobRepository, sharedBlobRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public StoragePathReplacer storagePathReplacer(StorageProperties properties) {
        var output = properties.getOutput();
        return new StoragePathReplacer(output.getPath(), output.getFilename());
    }

    @Bean
    @ConditionalOnMissingBean(StorageSystem.class)
    @ConditionalOnClass(LocalStorageSystem.class)
    @ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "local")
    public LocalStorageSystem localStorageSystem(StorageProperties properties) {
        var local = properties.getLocal();
        return new LocalStorageSystem(local.getDirectory(), properties.getBaseUrl());
    }

    @Bean
    @ConditionalOnMissingBean(StorageSystem.class)
    @ConditionalOnClass(AliyunStorageSystem.class)
    @ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "aliyun")
    public StorageSystem storageSystem(StorageProperties properties,
                                       @Autowired(required = false) StoragePathReplacer pathReplacer) {
        var aliyun = properties.getAliyun();
        var ass = new AliyunStorageSystem(properties.getBaseUrl());
        ass.setAccessKeyId(aliyun.getAccessKeyId());
        ass.setAccessKeySecret(aliyun.getAccessKeySecret());
        ass.setEndpoint(aliyun.getEndpoint());
        ass.setBucketName(aliyun.getBucketName());
        ass.setPathReplacer(pathReplacer);
        return ass;
    }

    @Bean
    @ConditionalOnMissingBean(StorageSystem.class)
    @ConditionalOnClass(QiniuStorageSystem.class)
    @ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "qiniu")
    public StorageSystem qiniuStorageSystem(StorageProperties properties,
                                            @Autowired(required = false) StoragePathReplacer pathReplacer) {
        var qiniu = properties.getQiniu();
        var qss = new QiniuStorageSystem(properties.getBaseUrl());
        qss.setPathReplacer(pathReplacer);
        qss.setAccessKey(qiniu.getAccessKey());
        qss.setSecretKey(qiniu.getSecretKey());
        qss.setBucket(qiniu.getBucket());
        qss.setRegion(qiniu.getRegion());
        return qss;
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
                    .addResourceLocations("file:" + properties.getLocal().getDirectory())
                    .setCachePeriod(3000)
                    .resourceChain(true)
                    .addResolver(new EncodedResourceResolver());
        }
    }
}
