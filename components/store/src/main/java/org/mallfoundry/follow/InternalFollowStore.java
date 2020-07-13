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

package org.mallfoundry.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "mf_follow_store")
@IdClass(InternalFollowStoreId.class)
public class InternalFollowStore implements FollowStore {

    @Id
    @Column(name = "follower_id_")
    private String followerId;

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "logo_")
    private String logo;

    @Column(name = "name_")
    private String name;

    @Column(name = "follow_time_")
    private Date followTime;

    public InternalFollowStore(String followerId, String id) {
        this.followerId = followerId;
        this.id = id;
        this.followTime = new Date();
    }
}
