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

package org.mallfoundry.data;

import java.util.Iterator;
import java.util.List;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface SliceList<T> extends Iterable<T> {

    int getPage();

    int getLimit();

    List<T> getElements();

    int getSize();

    int getTotalPages();

    long getTotalSize();

    @Override
    default Iterator<T> iterator() {
        return this.getElements().iterator();
    }
}
