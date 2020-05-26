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

package org.mallfoundry.store.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.InternalStore;
import org.mallfoundry.store.StoreQuery;
import org.mallfoundry.store.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@Repository
public interface JpaStoreRepository
        extends StoreRepository,
        JpaRepository<InternalStore, String>,
        JpaSpecificationExecutor<InternalStore> {

    @Override
    default SliceList<InternalStore> findAll(StoreQuery storeQuery) {
        Page<InternalStore> page = this.findAll((Specification<InternalStore>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(storeQuery.getOwnerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("ownerId"), storeQuery.getOwnerId()));
            }

            return predicate;
        }, PageRequest.of(storeQuery.getPage() - 1, storeQuery.getLimit()));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(storeQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
