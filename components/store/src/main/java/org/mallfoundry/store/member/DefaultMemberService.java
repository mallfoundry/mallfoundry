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
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.util.Copies;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

// store.member.auto-join-conditional=order_placed
// store.member.auto-join-conditional=order_paid
public class DefaultMemberService implements MemberService, MemberProcessorInvoker {

    private List<MemberProcessor> processors = Collections.emptyList();


    private final UserService userService;

    private final MemberRepository memberRepository;

    public DefaultMemberService(UserService userService, MemberRepository memberRepository) {
        this.userService = userService;
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
    public MemberId createMemberId(StoreId storeId, String memberId) {
        return new ImmutableMemberId(storeId.getTenantId(), storeId.getId(), memberId);
    }

    @Override
    public MemberId createMemberId(String storeId, String memberId) {
        return new ImmutableMemberId(null, storeId, memberId);
    }

    @Override
    public Member createMember(MemberId memberId) {
        return this.memberRepository.create(memberId);
    }

    public User requiredUser(Member member) {
        var userId = this.userService.createUserId(member.getTenantId(), member.getId());
        return this.userService.getUser(userId);
    }

    private Member joinMemberPeek(Member member) {
        var user = this.requiredUser(member);
        member.setTenantId(user.getTenantId());
        member.setAvatar(user.getAvatar());
        member.setCountryCode(user.getCountryCode());
        member.setPhone(user.getPhone());
        member.setCreatedTime(user.getCreatedTime());
        if (StringUtils.isBlank(member.getNickname())) {
            member.setNickname(user.getNickname());
        }
        if (Objects.isNull(member.getGender())) {
            member.setGender(user.getGender());
        }
        member.join();
        return member;
    }

    @Override
    public Member joinMember(Member member) {
        return this.memberRepository.findById(member.toId())
                .orElseGet(() -> Function.<Member>identity()
                        .compose(this.memberRepository::save)
                        .compose(this::joinMemberPeek)
                        .compose(this::invokePreProcessBeforeAddMember)
                        .apply(member));

    }

    @Override
    public Optional<Member> getMember(MemberId id) {
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

    private Member requiredMember(MemberId id) {
        return this.memberRepository.findById(id)
                .orElseThrow();
    }

    private Member updateMember(Member source, Member dest) {
        Copies.notBlank(source::getCountryCode).trim(dest::setCountryCode)
                .notBlank(source::getPhone).trim(dest::setPhone)
                .notBlank(source::getNickname).trim(dest::setNickname)
                .notBlank(source::getNotes).trim(dest::setNotes)
                .notNull(source::getBirthdate).set(source::setBirthdate);
        return dest;
    }

    @Override
    public Member updateMember(Member source) {
        return Function.<Member>identity()
                .compose(this.memberRepository::save)
                .<Member>compose(aMember -> this.updateMember(source, aMember))
                .compose(this::requiredMember)
                .apply(this.createMemberId(source.getStoreId(), source.getId()));
    }

    @Override
    public void deleteMember(MemberId id) {
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
