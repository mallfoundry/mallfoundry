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

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.util.Copies;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// store.member.auto-join-conditional=order_placed
// store.member.auto-join-conditional=order_paid
public class DefaultMemberService implements MemberService, MemberProcessorInvoker {

    private List<MemberProcessor> processors;

    private final MemberRepository memberRepository;

    public DefaultMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void setProcessors(List<MemberProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public MemberQuery createMemberQuery() {
        return new DefaultMemberQuery();
    }

    @Override
    public MemberId createMemberId(String storeId, String memberId) {
        return new ImmutableMemberId(storeId, memberId);
    }

    @Override
    public Member createMember(String id) {
        return this.memberRepository.create(id);
    }

    @Override
    public Member addMember(Member member) {
        return Function.<Member>identity()
                .compose(this.memberRepository::save)
                .compose(this::invokePreProcessBeforeAddMember)
                .apply(member);
    }

    @Override
    public Optional<Member> getMember(String id) {
        return this.memberRepository.findById(id)
                .map(this::invokePostProcessAfterGetMember);
    }

    @Override
    public SliceList<Member> getMembers(MemberQuery query) {
        return Function.<SliceList<Member>>identity()
                .compose(this.memberRepository::findAll)
                .compose(this::invokePreProcessBeforeGetMembers)
                .apply(query);
    }

    private Member requiredMember(String id) {
        return this.memberRepository.findById(id)
                .orElseThrow();
    }

    private Member updateMember(Member source, Member dest) {
        Copies.notBlank(source::getCountryCode).trim(dest::setCountryCode);
        Copies.notBlank(source::getPhone).trim(dest::setPhone);
        Copies.notBlank(source::getNickname).trim(dest::setNickname);
        Copies.notBlank(source::getNotes).trim(dest::setNotes);
        return dest;
    }

    @Override
    public Member updateMember(Member source) {
        return Function.<Member>identity()
                .compose(this.memberRepository::save)
                .<Member>compose(aMember -> this.updateMember(source, aMember))
                .compose(this::requiredMember)
                .apply(source.getId());
    }

    @Override
    public void deleteMember(String id) {
        var member = Function.<Member>identity()
                .compose(this::invokePreProcessBeforeDeleteMember)
                .compose(this::requiredMember)
                .apply(id);
        this.memberRepository.delete(member);
    }

    @Override
    public Member invokePreProcessBeforeAddMember(Member member) {
        return Processors.stream(this.processors)
                .map(MemberProcessor::preProcessBeforeAddMember)
                .apply(member);
    }

    @Override
    public Member invokePostProcessAfterGetMember(Member member) {
        return Processors.stream(this.processors)
                .map(MemberProcessor::postProcessAfterGetMember)
                .apply(member);
    }

    @Override
    public MemberQuery invokePreProcessBeforeGetMembers(MemberQuery query) {
        return Processors.stream(this.processors)
                .map(MemberProcessor::preProcessBeforeGetMembers)
                .apply(query);
    }

    @Override
    public Member invokePreProcessBeforeUpdateMember(Member member) {
        return Processors.stream(this.processors)
                .map(MemberProcessor::preProcessBeforeUpdateMember)
                .apply(member);
    }

    @Override
    public Member invokePreProcessBeforeDeleteMember(Member member) {
        return Processors.stream(this.processors)
                .map(MemberProcessor::preProcessBeforeDeleteMember)
                .apply(member);
    }
}
