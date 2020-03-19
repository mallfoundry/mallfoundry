/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.data;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PageLimit {

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_LIMIT = 20;

    public static final int MIN_LIMIT = 1;

    public static final int MAX_LIMIT = 100;

    private Integer page = DEFAULT_PAGE;

    private Integer limit = DEFAULT_LIMIT;

    public void setPage(Integer page) {
        if (Objects.nonNull(page)) {
            this.page = Math.max(DEFAULT_PAGE, page);
        }
    }

    public void setLimit(Integer limit) {
        if (Objects.nonNull(limit)) {
            this.limit = Math.min(Math.max(MIN_LIMIT, limit), MAX_LIMIT);
        }
    }
}
