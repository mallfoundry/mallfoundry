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

package org.mallfoundry.order.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.InternalOrder;
import org.mallfoundry.order.OrderQuery;
import org.mallfoundry.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaOrderRepository
        extends OrderRepository,
        JpaRepository<InternalOrder, String>,
        JpaSpecificationExecutor<InternalOrder> {

    @Override
    List<InternalOrder> findAllById(Iterable<String> iterable);

    default Specification<InternalOrder> createSpecification(OrderQuery orderQuery) {
        return (Specification<InternalOrder>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(orderQuery.getCustomerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("customerId"), orderQuery.getCustomerId()));
            }

            if (CollectionUtils.isNotEmpty(orderQuery.getStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("status")).value(orderQuery.getStatuses()));
            }

            if (Objects.nonNull(orderQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), orderQuery.getStoreId()));
            }

            return predicate;
        };
    }

    @Override
    default SliceList<InternalOrder> findAll(OrderQuery orderQuery) {

        Page<InternalOrder> page = this.findAll(this.createSpecification(orderQuery),
                PageRequest.of(orderQuery.getPage() - 1, orderQuery.getLimit(), Sort.by(Sort.Order.desc("createdTime"))));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(orderQuery.getLimit())
                .totalSize(page.getTotalElements());
    }

    @Override
    default long count(OrderQuery orderQuery) {
        return this.count(this.createSpecification(orderQuery));
    }
}
