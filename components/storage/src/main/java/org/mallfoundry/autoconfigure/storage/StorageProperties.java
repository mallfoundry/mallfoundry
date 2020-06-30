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

package org.mallfoundry.autoconfigure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties(prefix = "mall.storage")
public class StorageProperties {

    private SystemType type;

    private String baseUrl;

    @NestedConfigurationProperty
    private Local local = new Local();

    @NestedConfigurationProperty
    private Aliyun aliyun = new Aliyun();

    @NestedConfigurationProperty
    private Ftp ftp = new Ftp();

    @NestedConfigurationProperty
    private Output output = new Output();

    public enum SystemType {
        LOCAL, FTP, ALIYUN
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
    static class Output {
        private String path = "/{yyyy}/{MM}/{dd}";
        private String filename = "{uuid}.{ext}";
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
