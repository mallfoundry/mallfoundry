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

package org.mallfoundry.follow;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public interface FollowProductRepository {


    Optional<InternalFollowProduct> findById(InternalFollowProductId id);

    InternalFollowProduct save(InternalFollowProduct followProduct);

    boolean existsById(InternalFollowProductId id);

    void delete(InternalFollowProduct followProduct);

    SliceList<InternalFollowProduct> findAll(FollowProductQuery query);

    long count(FollowProductQuery query);

    long countById(String id);
}
