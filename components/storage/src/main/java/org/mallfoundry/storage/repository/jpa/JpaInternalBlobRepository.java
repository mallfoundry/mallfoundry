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

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobQuery;
import org.mallfoundry.storage.InternalBlob;
import org.mallfoundry.storage.InternalBlobId;
import org.mallfoundry.storage.InternalBlobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaInternalBlobRepository
        extends InternalBlobRepository,
        JpaRepository<InternalBlob, InternalBlobId>,
        JpaSpecificationExecutor<InternalBlob> {

    @Modifying
    @Query("delete from InternalBlob where bucket=?1 and path in (?2)")
    @Override
    void deleteAllByBucketAndPaths(String bucket, List<String> paths);

    @Override
    default SliceList<Blob> findAll(BlobQuery blobQuery) {
        Page<InternalBlob> page = this.findAll((Specification<InternalBlob>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(blobQuery.getBucket())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bucket"), blobQuery.getBucket()));
            }

            if (Objects.nonNull(blobQuery.getPath())) {
                predicate.getExpressions()
                        .add(criteriaBuilder.equal(root.get("parent"),
                                new InternalBlob(new InternalBlobId(blobQuery.getBucket(), blobQuery.getPath()))));
            } else {
                predicate.getExpressions().add(criteriaBuilder.isNull(root.get("parent")));
            }

            if (Objects.nonNull(blobQuery.getType())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), blobQuery.getType()));
            }

            return predicate;
        }, PageRequest.of(blobQuery.getPage() - 1, blobQuery.getLimit()));

        return PageList.of(new ArrayList<Blob>(page.getContent()))
                .page(page.getNumber() + 1)
                .limit(blobQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
