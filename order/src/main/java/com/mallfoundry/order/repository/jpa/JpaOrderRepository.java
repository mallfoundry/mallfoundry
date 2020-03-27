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

package com.mallfoundry.order.repository.jpa;

import com.mallfoundry.data.PageList;
import com.mallfoundry.data.SliceList;
import com.mallfoundry.order.Order;
import com.mallfoundry.order.OrderQuery;
import com.mallfoundry.order.OrderRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

public interface JpaOrderRepository
        extends OrderRepository,
        JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Override
    <S extends Order> List<S> saveAll(Iterable<S> iterable);

    @Override
    List<Order> findAllById(Iterable<Long> iterable);

    @Override
    default SliceList<Order> findAll(OrderQuery orderQuery) {

        Page<Order> page = this.findAll((Specification<Order>) (root, query, criteriaBuilder) -> {
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
        }, PageRequest.of(orderQuery.getPage() - 1, orderQuery.getLimit()));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(orderQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
