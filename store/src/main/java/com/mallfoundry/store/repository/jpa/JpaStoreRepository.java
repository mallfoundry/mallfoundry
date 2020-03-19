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

package com.mallfoundry.store.repository.jpa;

import com.mallfoundry.data.PageList;
import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.Store;
import com.mallfoundry.store.StoreQuery;
import com.mallfoundry.store.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaStoreRepository
        extends StoreRepository,
        JpaRepository<Store, String>,
        JpaSpecificationExecutor<Store> {


    @Override
    default SliceList<Store> findAll(StoreQuery storeQuery) {
        Page<Store> page = this.findAll((Specification<Store>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(storeQuery.getStorekeeperId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storekeeperId"), storeQuery.getStorekeeperId()));
            }

            return predicate;
        }, PageRequest.of(storeQuery.getPage() - 1, storeQuery.getLimit()));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(storeQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
