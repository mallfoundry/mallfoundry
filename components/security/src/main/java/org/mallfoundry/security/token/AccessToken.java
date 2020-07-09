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

package org.mallfoundry.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mf_access_token")
public class AccessToken {

    @Id
    @Column(name = "username_")
    private String username;

    @Column(name = "token_")
    @JsonProperty("access_token")
    private String token;

    @Column(name = "type_")
    @JsonProperty("token_type")
    private String type;

    public static AccessToken newToken(String username, String tokenValue) {
        AccessToken token = new AccessToken();
        token.setToken(tokenValue);
        token.setType("Bearer");
        token.setUsername(username);
        return token;
    }
}
