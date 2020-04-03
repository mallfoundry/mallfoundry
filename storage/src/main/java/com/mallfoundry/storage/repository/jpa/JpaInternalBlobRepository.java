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

package com.mallfoundry.storage.repository.jpa;

import com.mallfoundry.data.PageList;
import com.mallfoundry.data.SliceList;
import com.mallfoundry.storage.Blob;
import com.mallfoundry.storage.BlobQuery;
import com.mallfoundry.storage.InternalBlob;
import com.mallfoundry.storage.InternalBlobId;
import com.mallfoundry.storage.InternalBlobRepository;
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
    void deleteByBucketAndPaths(String bucket, List<String> paths);

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
                .page(page.getNumber())
                .limit(blobQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
