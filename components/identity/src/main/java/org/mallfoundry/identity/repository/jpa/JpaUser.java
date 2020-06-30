/*
 * Copyright 2019 the original author or authors.
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

package org.mallfoundry.identity.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.jpa.convert.StringListConverter;
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

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "country_code_")
    private String countryCode;

    @Column(name = "mobile_", unique = true)
    private String mobile;

    @Column(name = "password_")
    private String password;

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
