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

package org.mallfoundry.following.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.following.FollowStoreQuery;
import org.mallfoundry.following.StoreFollowing;
import org.mallfoundry.following.StoreFollowingId;
import org.mallfoundry.following.StoreFollowingRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DelegatingJpaStoreFollowingRepository implements StoreFollowingRepository {

    private final JpaStoreFollowingRepository repository;

    public DelegatingJpaStoreFollowingRepository(JpaStoreFollowingRepository repository) {
        this.repository = repository;
    }

    @Override
    public StoreFollowing create(String followerId, String storeId) {
        return new JpaStoreFollowing(followerId, storeId);
    }

    @Override
    public Optional<StoreFollowing> findById(StoreFollowingId followingId) {
        return CastUtils.cast(this.repository.findById(JpaStoreFollowingId.of(followingId)));
    }

    @Override
    public StoreFollowing save(StoreFollowing following) {
        return this.repository.save(JpaStoreFollowing.of(following));
    }

    @Override
    public boolean exists(StoreFollowing following) {
        return this.repository.existsById(JpaStoreFollowingId.of(following.getFollowerId(), following.getStoreId()));
    }

    @Override
    public void delete(StoreFollowing following) {
        this.repository.delete(JpaStoreFollowing.of(following));
    }

    @Override
    public SliceList<StoreFollowing> findAll(FollowStoreQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(page.getNumber()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public long count(FollowStoreQuery query) {
        return this.repository.count(query);
    }
}
