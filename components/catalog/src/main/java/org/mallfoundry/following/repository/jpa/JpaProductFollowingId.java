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
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.following.ProductFollowingId;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class JpaProductFollowingId implements ProductFollowingId {
    private String followerId;
    private String productId;

    public JpaProductFollowingId(String followerId, String productId) {
        this.followerId = followerId;
        this.productId = productId;
    }

    public static JpaProductFollowingId of(String followerId, String productId) {
        return new JpaProductFollowingId(followerId, productId);
    }

    public static JpaProductFollowingId of(ProductFollowingId id) {
        return new JpaProductFollowingId(id.getFollowerId(), id.getProductId());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaProductFollowingId)) {
            return false;
        }
        JpaProductFollowingId that = (JpaProductFollowingId) object;
        return Objects.equals(followerId, that.followerId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, productId);
    }
}
