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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.following.StoreFollowingId;

@Getter
@Setter
public class JpaStoreFollowingId implements StoreFollowingId {
    private String followerId;
    private String storeId;

    public JpaStoreFollowingId(String followerId, String storeId) {
        this.followerId = followerId;
        this.storeId = storeId;
    }

    public static JpaStoreFollowingId of(String followerId, String productId) {
        return new JpaStoreFollowingId(followerId, productId);
    }

    public static JpaStoreFollowingId of(StoreFollowingId id) {
        return new JpaStoreFollowingId(id.getFollowerId(), id.getStoreId());
    }
}
