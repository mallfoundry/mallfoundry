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

package com.mallfoundry.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@JsonPropertyOrder({"offset", "limit", "size", "total", "elements"})
public class OffsetPagedList<T> extends PagedList<T> {

    @Getter
    @Setter
    private long offset;

    @Getter
    @Setter
    private long limit;

    public static <T> OffsetPagedList<T> of(List<T> list, long offset, long limit, long total) {
        // unmodifiable list
        List<T> uList = Collections.unmodifiableList(list);
        OffsetPagedList<T> page = new OffsetPagedList<>();
        page.setTotal(total);
        page.setSize(uList.size());
        page.setElements(uList);
        page.setOffset(offset);
        page.setLimit(limit);
        return page;
    }
}
