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

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PagedList<T> implements Iterable<T> {

    @Getter
    @Setter
    private long total;

    @Getter
    @Setter
    private long size;

    @Setter
    private List<T> elements;

    public List<T> getElements() {
        return this.elements == null ? Collections.emptyList() : this.elements;
    }

    @Override
    public Iterator<T> iterator() {
        return this.getElements().iterator();
    }

    public static <T> PagedList<T> of(List<T> list, long total) {
        // unmodifiable list
        List<T> uList = Collections.unmodifiableList(list);
        PagedList<T> page = new PagedList<>();
        page.setTotal(total);
        page.setSize(uList.size());
        page.setElements(uList);
        return page;
    }

    public static <T> PagedList<T> empty() {
        PagedList<T> page = new PagedList<>();
        page.setTotal(0);
        page.setSize(0);
        page.setElements(Collections.emptyList());
        return page;
    }


}
