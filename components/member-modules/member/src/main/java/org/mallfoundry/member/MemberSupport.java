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

import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class MemberSupport implements MutableMember {

    @Override
    public MemberId toId() {
        return new ImmutableMemberId(this.getTenantId(), this.getStoreId(), this.getId());
    }

    @Override
    public void join() {
        if (Objects.isNull(this.getBirthdate())) {
            this.setBirthdate(new Date());
        }
        this.setJoinedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final MemberSupport member;

        protected BuilderSupport(MemberSupport member) {
            this.member = member;
        }

        @Override
        public Builder id(String id) {
            this.member.setId(id);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.member.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.member.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder phone(String phone) {
            this.member.setPhone(phone);
            return this;
        }

        @Override
        public Builder nickname(String nickname) {
            this.member.setNickname(nickname);
            return this;
        }

        @Override
        public Builder gender(Gender gender) {
            this.member.setGender(gender);
            return this;
        }

        @Override
        public Builder birthdate(Date birthdate) {
            this.member.setBirthdate(birthdate);
            return this;
        }

        @Override
        public Builder tags(List<String> tags) {
            this.member.setTags(tags);
            return this;
        }

        @Override
        public Builder notes(String notes) {
            this.member.setNotes(notes);
            return this;
        }

        @Override
        public Member build() {
            return this.member;
        }
    }
}
