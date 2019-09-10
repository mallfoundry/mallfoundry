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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shop.storage")
public class ShopStorageProperties {

    @Setter
    private Http http;

    @Setter
    private Store store;

    public Store getStore() {
        if (this.store == null) {
            this.store = new Store();
        }
        return this.store;
    }

    public Http getHttp() {
        if (this.http == null) {
            this.http = new Http();
        }
        return this.http;
    }

    public enum StoreType {
        LOCAL, FTP, ALI_CLOUD_OOS
    }

    public static class Store {

        @Getter
        @Setter
        private StoreType type;

        @Getter
        @Setter
        private String directory;
    }


    public static class Http {

        @Getter
        @Setter
        private String baseUrl;
    }
}
