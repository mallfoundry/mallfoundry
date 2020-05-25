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

package org.mallfoundry.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"offset", "limit", "size", "total", "elements"})
public class OffsetList<T> implements Iterable<T> {

    private long offset;

    private long limit;

    private long size;

    private long total;

    private List<T> elements;

    private OffsetList(List<T> elements, long offset, long limit, long total) {
        List<T> list = Objects.nonNull(elements) ? Collections.unmodifiableList(elements) : Collections.emptyList();
        OffsetList<T> page = new OffsetList<>();
        page.setTotal(total);
        page.setSize(list.size());
        page.setElements(list);
        page.setOffset(offset);
        page.setLimit(limit);
    }

    @Override
    public Iterator<T> iterator() {
        return this.getElements().iterator();
    }

    public static <T> OffsetList<T> of(List<T> list, long total) {
        // unmodifiable list
        List<T> uList = Collections.unmodifiableList(list);
        OffsetList<T> page = new OffsetList<>();
        page.setTotal(total);
        page.setSize(uList.size());
        page.setElements(uList);
        return page;
    }

    public static <T> OffsetList<T> empty() {
        OffsetList<T> page = new OffsetList<>();
        page.setTotal(0);
        page.setSize(0);
        page.setElements(Collections.emptyList());
        return page;
    }

    public static <T> OffsetList<T> of(List<T> elements, long offset, long limit, long total) {
        return new OffsetList<>(elements, offset, limit, total);
    }
}
