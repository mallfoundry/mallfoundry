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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class PageList<T> implements SliceList<T> {

    private int page;

    private int limit;

    private long size;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_size")
    private long totalSize;

    private List<T> elements;

    public PageList(List<T> elements) {
        this.elements = Objects.isNull(elements) ? Collections.emptyList() : Collections.unmodifiableList(elements);
        this.size = this.elements.size();
    }

    public static <T> PageList<T> of(List<T> elements) {
        return new PageList<>(elements);
    }

    public static <T> PageList<T> empty() {
        return new PageList<>(null);
    }

    public PageList<T> page(int page) {
        this.page = Math.max(page, PageLimit.DEFAULT_PAGE);
        return this;
    }

    public PageList<T> limit(int limit) {
        this.limit = limit;
        this.computeTotalPages();
        return this;
    }

    public PageList<T> totalSize(long totalSize) {
        this.totalSize = totalSize;
        this.computeTotalPages();
        return this;
    }

    private void computeTotalPages() {
        if (limit <= 0) {
            return;
        }
        this.totalPages = (int) ((totalSize + limit - 1) / limit);
    }
}
