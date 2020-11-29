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

package org.mallfoundry.rest.store.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.identity.Gender;
import org.mallfoundry.store.member.Member;

import java.util.Date;

@Getter
@Setter
public class MemberRequest {
    private String id;
    private String nickname;
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String notes;

    public Member assignTo(Member member) {
        return member.toBuilder()
                .id(this.id)
                .nickname(this.nickname).gender(this.gender)
                .birthdate(this.birthdate).notes(this.notes)
                .build();
    }
}
