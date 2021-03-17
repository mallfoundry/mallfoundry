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

package org.mallfoundry.member;

import org.mallfoundry.identity.Gender;
import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

public interface Member extends StoreOwnership, ObjectBuilder.ToBuilder<Member.Builder> {

    MemberId toId();

    String getId();

    void setId(String id);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getPhone();

    void setPhone(String phone);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    Date getBirthdate();

    void setBirthdate(Date birthdate);

    List<String> getTags();

    void setTags(List<String> tags);

    String getNotes();

    void setNotes(String notes);

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);

    Date getJoinedTime();

    void join();

    interface Builder extends ObjectBuilder<Member> {

        Builder id(String id);

        Builder storeId(String storeId);

        Builder countryCode(String countryCode);

        Builder phone(String phone);

        Builder nickname(String nickname);

        Builder gender(Gender gender);

        Builder birthdate(Date birthdate);

        Builder tags(List<String> tags);

        Builder notes(String notes);
    }
}
