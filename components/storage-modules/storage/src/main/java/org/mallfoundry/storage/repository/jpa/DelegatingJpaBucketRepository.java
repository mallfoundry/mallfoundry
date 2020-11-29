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

import org.mallfoundry.storage.Bucket;
import org.mallfoundry.storage.BucketId;
import org.mallfoundry.storage.BucketRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaBucketRepository implements BucketRepository {

    private final JpaBucketRepository repository;

    public DelegatingJpaBucketRepository(JpaBucketRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bucket create(BucketId bucketId) {
        return new JpaBucket(bucketId);
    }

    @Override
    public Optional<Bucket> findById(BucketId bucketId) {
        return CastUtils.cast(this.repository.findById(bucketId.getId()));
    }

    @Override
    public Bucket save(Bucket bucket) {
        return this.repository.save(JpaBucket.of(bucket));
    }

    @Override
    public void delete(Bucket bucket) {
        this.repository.delete(JpaBucket.of(bucket));
    }
}
