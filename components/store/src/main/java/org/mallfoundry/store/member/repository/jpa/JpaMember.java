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

package org.mallfoundry.store.member.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.identity.Gender;
import org.mallfoundry.store.member.Member;
import org.mallfoundry.store.member.MemberId;
import org.mallfoundry.store.member.MemberSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store_member")
public class JpaMember extends MemberSupport {

    @NotNull
    @Id
    @Column(name = "id_")
    private String id;

    @NotNull
    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "tenant_id_")
    private String tenantId;

    @NotBlank
    @Column(name = "avatar_")
    private String avatar;

    @NotNull
    @Column(name = "gender_")
    private Gender gender;

    @NotBlank
    @Column(name = "nickname_")
    private String nickname;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate_")
    private Date birthdate;

    @NotBlank
    @Column(name = "country_code_")
    private String countryCode;

    @NotBlank
    @Column(name = "phone_")
    private String phone;

    @Column(name = "notes_")
    private String notes;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;

    @NotNull
    @Column(name = "joined_time_")
    private Date joinedTime;

    @NotNull
    @Convert(converter = StringListConverter.class)
    @Column(name = "tags_")
    private List<String> tags = new ArrayList<>();

    public JpaMember(MemberId id) {
        this.id = id.getMemberId();
        this.storeId = id.getStoreId();
    }

    public static JpaMember of(Member member) {
        if (member instanceof JpaMember) {
            return (JpaMember) member;
        }

        var target = new JpaMember();
        BeanUtils.copyProperties(member, target);
        return target;
    }
}
