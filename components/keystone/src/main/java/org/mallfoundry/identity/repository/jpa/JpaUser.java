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

package org.mallfoundry.identity.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.identity.Gender;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_identity_user")
public class JpaUser extends UserSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "username_", unique = true)
    private String username;

    @Column(name = "avatar_")
    private String avatar;

    @Column(name = "gender_")
    private Gender gender;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "country_code_")
    private String countryCode;

    @Column(name = "mobile_", unique = true)
    private String mobile;

    @Column(name = "password_")
    private String password;

    @Column(name = "immutable_")
    private boolean immutable = false;

    @Column(name = "email_", unique = true)
    private String email;

    @Column(name = "enabled_")
    private boolean enabled;

    @Convert(converter = StringListConverter.class)
    @Column(name = "authorities_")
    private List<String> authorities = new ArrayList<>();

    @Column(name = "created_time_")
    private Date createdTime;

    public JpaUser(String id) {
        this.id = id;
    }

    public static JpaUser of(User user) {
        if (user instanceof JpaUser) {
            return (JpaUser) (user);
        }

        var target = new JpaUser();
        BeanUtils.copyProperties(user, target);
        return target;
    }
}
