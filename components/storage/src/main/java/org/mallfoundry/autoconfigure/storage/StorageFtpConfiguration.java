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

import org.mallfoundry.storage.StoragePathReplacer;
import org.mallfoundry.storage.ftp.FtpClientFactory;
import org.mallfoundry.storage.ftp.FtpStorageSystem;
import org.mallfoundry.storage.ftp.FtpTemplate;
import org.mallfoundry.storage.ftp.pool2.factory.FtpClientPooledConfiguration;
import org.mallfoundry.storage.ftp.pool2.factory.FtpClientPooledFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "mall.storage", name = "type", havingValue = "ftp")
public class StorageFtpConfiguration {

    private FtpClientPooledConfiguration createFtpClientPooledConfig(StorageProperties.Ftp ftp) {
        var config = new FtpClientPooledConfiguration();
        config.setHostname(ftp.getHostname());
        config.setPort(ftp.getPort());
        config.setUsername(ftp.getUsername());
        config.setPassword(ftp.getPassword());
        config.setControlEncoding(ftp.getControlEncoding());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(FtpClientFactory.class)
    @ConditionalOnClass(FtpClientPooledFactory.class)
    public FtpClientPooledFactory ftpClientPooledFactory(StorageProperties properties) throws ClassNotFoundException {
        var config = this.createFtpClientPooledConfig(properties.getFtp());
        return new FtpClientPooledFactory(config);
    }

    @Bean
    @ConditionalOnMissingBean(FtpTemplate.class)
    @ConditionalOnClass(FtpTemplate.class)
    public FtpTemplate ftpTemplate(FtpClientFactory ftpClientFactory) {
        return new FtpTemplate(ftpClientFactory);
    }

    @Bean
    @ConditionalOnClass(FtpStorageSystem.class)
    public FtpStorageSystem ftpStorageSystem(FtpTemplate ftpTemplate, StorageProperties properties,
                                             @Autowired(required = false) StoragePathReplacer pathReplacer) {
        var fss = new FtpStorageSystem(properties.getBaseUrl());
        fss.setFtpTemplate(ftpTemplate);
        fss.setBaseDirectory(properties.getFtp().getBaseDirectory());
        fss.setPathReplacer(pathReplacer);
        return fss;
    }

}
