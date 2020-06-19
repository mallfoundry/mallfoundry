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

package org.mallfoundry.browsing.repository.jpa;


import org.mallfoundry.browsing.BrowsingProductQuery;
import org.mallfoundry.browsing.InternalBrowsingProduct;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
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
import java.util.Optional;

@Repository
public interface JpaBrowsingProductRepositoryDelegate
        extends JpaRepository<InternalBrowsingProduct, JpaBrowsingProductId>,
        JpaSpecificationExecutor<InternalBrowsingProduct> {

    InternalBrowsingProduct save(InternalBrowsingProduct browsingProduct);

    Optional<InternalBrowsingProduct> findByIdAndBrowserId(String id, String browserId);

    List<InternalBrowsingProduct> findAllByIdInAndBrowserId(Iterable<String> ids, String browserId);

    default Specification<InternalBrowsingProduct> createSpecification(BrowsingProductQuery bpQuery) {
        return (Specification<InternalBrowsingProduct>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(bpQuery.getBrowserId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("browserId"), bpQuery.getBrowserId()));
            }
            return predicate;
        };
    }

    default SliceList<InternalBrowsingProduct> findAll(BrowsingProductQuery query) {
        Page<InternalBrowsingProduct> page = this.findAll(this.createSpecification(query),
                PageRequest.of(query.getPage() - 1, query.getLimit(), Sort.by(Sort.Order.desc("browsingTime"))));
        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(query.getLimit())
                .totalSize(page.getTotalElements());
    }

    default long count(BrowsingProductQuery query) {
        return this.count(this.createSpecification(query));
    }

}
