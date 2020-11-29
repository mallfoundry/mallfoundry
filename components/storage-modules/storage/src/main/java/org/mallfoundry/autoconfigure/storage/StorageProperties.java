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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties(prefix = "mallfoundry.storage")
public class StorageProperties {

    private SystemType type;

    private String baseUrl;

    @NestedConfigurationProperty
    private Local local = new Local();

    @NestedConfigurationProperty
    private Aliyun aliyun = new Aliyun();

    @NestedConfigurationProperty
    private Qiniu qiniu = new Qiniu();

    @NestedConfigurationProperty
    private Ftp ftp = new Ftp();

    @NestedConfigurationProperty
    private Output output = new Output();

    public enum SystemType {
        LOCAL, FTP, ALIYUN, QINIU
    }

    @Getter
    @Setter
    static class Local {
        private String directory;
    }

    @Getter
    @Setter
    static class Aliyun {
        private String accessKeyId;
        private String accessKeySecret;
        private String endpoint;
        private String bucketName;
    }

    @Getter
    @Setter
    static class Qiniu {
        private String accessKey;
        private String secretKey;
        private String region;
        private String bucket;
    }

    @Getter
    @Setter
    static class Output {
        private String path = "/{yyyy}/{MM}/{dd}";
        private String filename = "{name}.{extension}";
    }

    @Getter
    @Setter
    static class Ftp {
        private String hostname;
        private int port;
        private String username;
        private String password;
        private String controlEncoding = "utf-8";
        private String baseDirectory;
    }

}
