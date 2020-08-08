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

package org.mallfoundry.store.member;

import java.util.Optional;

public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public DefaultMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberQuery createMemberQuery() {
        return new DefaultMemberQuery();
    }

    @Override
    public Member createMember(String id) {
        return this.memberRepository.create(id);
    }

    @Override
    public Member addMember(Member member) {
        return this.memberRepository.save(member);
    }

    private Member requiredMember(String id) {
        return this.memberRepository.findById(id).orElseThrow();
    }

    @Override
    public Member updateMember(Member source) {
        var member = this.requiredMember(source.getId());

        return this.memberRepository.save(member);
    }

    @Override
    public Optional<Member> getMember(String id) {
        return this.memberRepository.findById(id);
    }

    @Override
    public void deleteMember(String id) {
        var member = this.requiredMember(id);
        this.memberRepository.delete(member);
    }
}
