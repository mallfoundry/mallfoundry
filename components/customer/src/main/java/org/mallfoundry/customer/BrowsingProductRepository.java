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

package org.mallfoundry.customer;

import org.mallfoundry.data.SliceList;

import java.util.List;
import java.util.Optional;

public interface BrowsingProductRepository {

    InternalBrowsingProduct save(InternalBrowsingProduct browsingProduct);

    Optional<InternalBrowsingProduct> findByIdAndBrowserId(String id, String browserId);

    List<InternalBrowsingProduct> findAllByIdInAndBrowserId(Iterable<String> ids, String browserId);

    SliceList<InternalBrowsingProduct> findAll(BrowsingProductQuery query);

    void delete(InternalBrowsingProduct browsingProduct);

    void deleteAll(Iterable<? extends InternalBrowsingProduct> browsingProducts);

    long count(BrowsingProductQuery query);
}
