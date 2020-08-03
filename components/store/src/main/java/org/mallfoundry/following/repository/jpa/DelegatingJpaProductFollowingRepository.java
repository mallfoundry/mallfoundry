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
import org.mallfoundry.following.FollowProductQuery;
import org.mallfoundry.following.ProductFollowing;
import org.mallfoundry.following.ProductFollowingId;
import org.mallfoundry.following.ProductFollowingRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DelegatingJpaProductFollowingRepository implements ProductFollowingRepository {
    private final JpaProductFollowingRepository repository;

    public DelegatingJpaProductFollowingRepository(JpaProductFollowingRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductFollowing create(String followerId, String productId) {
        return new JpaProductFollowing(followerId, productId);
    }

    @Override
    public Optional<ProductFollowing> findById(ProductFollowingId followingId) {
        return CastUtils.cast(this.repository.findById(JpaProductFollowingId.of(followingId)));
    }

    @Override
    public ProductFollowing save(ProductFollowing following) {
        return this.repository.save(JpaProductFollowing.of(following));
    }

    @Override
    public boolean exists(ProductFollowing following) {
        return this.repository.existsById(JpaProductFollowingId.of(following.getFollowerId(), following.getProductId()));
    }

    @Override
    public void delete(ProductFollowing following) {
        this.repository.delete(JpaProductFollowing.of(following));
    }

    @Override
    public SliceList<ProductFollowing> findAll(FollowProductQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(page.getNumber()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public long count(FollowProductQuery query) {
        return this.repository.count(query);
    }
}
