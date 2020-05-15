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

package com.mallfoundry.identity;

import com.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "identity_user")
public class InternalUser implements User {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "username_", unique = true)
    private String username;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "password_")
    private String password;

    @Column(name = "email_")
    private String email;

    @Column(name = "enabled_")
    private boolean enabled;

    @Convert(converter = StringListConverter.class)
    @Column(name = "authorities_")
    private List<String> authorities = new ArrayList<>();

    public InternalUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static InternalUser of(User user) {
        if (user instanceof InternalUser) {
            return (InternalUser) (user);
        }

        var target = new InternalUser();
        BeanUtils.copyProperties(user, target);
        return target;
    }

    @Override
    public void changePassword(String password) {
        this.setPassword(password);
    }
}
