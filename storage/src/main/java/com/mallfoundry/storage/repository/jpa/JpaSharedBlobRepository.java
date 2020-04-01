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

import com.mallfoundry.storage.SharedBlob;
import com.mallfoundry.storage.SharedBlobRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSharedBlobRepository
        extends SharedBlobRepository, JpaRepository<SharedBlob, String> {

    @Override
    default SharedBlob findByBlob(SharedBlob blob) {
        return DataAccessUtils.singleResult(this.findAll(Example.of(blob)));
    }
}
