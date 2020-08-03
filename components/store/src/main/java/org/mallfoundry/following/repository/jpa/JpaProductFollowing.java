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
import org.mallfoundry.following.ProductFollowing;
import org.mallfoundry.following.ProductFollowingSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_following_product")
@IdClass(JpaProductFollowingId.class)
public class JpaProductFollowing extends ProductFollowingSupport {

    @Id
    @Column(name = "follower_id_")
    private String followerId;

    @Id
    @Column(name = "product_id_")
    private String productId;

    @Column(name = "followed_time_")
    private Date followedTime;

    public JpaProductFollowing(String followerId, String productId) {
        this.followerId = followerId;
        this.productId = productId;
    }

    public static JpaProductFollowing of(String followerId, String productId) {
        return new JpaProductFollowing(followerId, productId);
    }

    public static JpaProductFollowing of(ProductFollowing following) {
        if (following instanceof JpaProductFollowing) {
            return (JpaProductFollowing) following;
        }
        var target = new JpaProductFollowing();
        BeanUtils.copyProperties(following, target);
        return target;
    }
}
