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

package org.mallfoundry.security.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.Authority;
import org.mallfoundry.security.AuthorityId;
import org.mallfoundry.security.AuthoritySupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_authority")
public class JpaAuthority extends AuthoritySupport {

    @Id
    @Column(name = "code_")
    private String code;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @OneToMany(targetEntity = JpaAuthority.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_code_")
    @OrderBy("position")
    private List<Authority> children = new ArrayList<>();

    @Column(name = "position_")
    private int position;

    public static JpaAuthority of(Authority authority) {
        if (authority instanceof JpaAuthority) {
            return (JpaAuthority) authority;
        }
        var target = new JpaAuthority();
        BeanUtils.copyProperties(authority, target);
        return target;
    }

    public JpaAuthority(AuthorityId authorityId) {
        this.code = authorityId.getCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaAuthority)) {
            return false;
        }
        JpaAuthority that = (JpaAuthority) object;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
