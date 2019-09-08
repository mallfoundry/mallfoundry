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

package com.github.shop.fs.store;

import com.github.shop.fs.FilePathUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStrategyStoreFilePathGenerator extends StoreFilePathGenerator {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public String storePath(String path) {
        return FilePathUtils.join(this.getDatePath(), path);
    }

    private String getDatePath() {
        return dateFormat.format(new Date());
    }

}
