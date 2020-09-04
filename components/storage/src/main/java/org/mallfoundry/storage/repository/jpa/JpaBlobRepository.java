/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.storage.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobQuery;
import org.mallfoundry.storage.ImmutableBlobPath;
import org.mallfoundry.util.PathUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public interface JpaBlobRepository extends JpaRepository<JpaBlob, String>, JpaSpecificationExecutor<JpaBlob> {

    void deleteAllByIdInOrIndexesIn(Collection<String> ids, Collection<String> indexes);

    void deleteAllByBucketId(String bucket);

    default SliceList<Blob> findAll(BlobQuery blobQuery) {
        Page<JpaBlob> page = this.findAll((Specification<JpaBlob>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(blobQuery.getBucketId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bucketId"), blobQuery.getBucketId()));
            }
            if (PathUtils.isRootPath(blobQuery.getPath())) {
                predicate.getExpressions().add(criteriaBuilder.isNull(root.get("parent")));
            } else {
                var path = new ImmutableBlobPath(blobQuery.getBucketId(), blobQuery.getPath());
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("parent"),
                        new JpaBlob().toBuilder().id(path.toId().getId()).build()));
            }
            if (CollectionUtils.isNotEmpty(blobQuery.getTypes())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("type")).value(blobQuery.getTypes()));
            }
            return predicate;
        }, PageRequest.of(blobQuery.getPage() - 1, blobQuery.getLimit()));

        return PageList.of(new ArrayList<Blob>(page.getContent()))
                .page(page.getNumber() + 1)
                .limit(blobQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
