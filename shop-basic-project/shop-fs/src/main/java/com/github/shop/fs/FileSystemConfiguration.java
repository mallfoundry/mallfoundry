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

package com.github.shop.fs;

import lombok.Getter;
import lombok.Setter;

/**
 * shop.fs.store.type=LOCAL,FTP
 * shop.fs.store.directory=d:/shop/fs
 * shop.fs.store.ftp.username
 * shop.fs.store.ftp.password
 * shop.fs.store.ftp.port
 * shop.fs.store.ftp.path
 */
public class FileSystemConfiguration {

    @Getter
    @Setter
    private Store store;

    public static class Store {

        @Getter
        @Setter
        private String directory;

        @Getter
        @Setter
        private FileStoreType type;
    }

}
