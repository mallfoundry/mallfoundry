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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.member.Member;
import org.mallfoundry.store.member.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/v1")
public class MemberResourceV1 {

    private final MemberService memberService;

    public MemberResourceV1(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("stores/{store_id}/members")
    public Member addMember(@PathVariable("store_id") String storeId,
                            @RequestBody MemberRequest request) {
        return Function.<Member>identity()
                .compose(this.memberService::addMember)
                .compose(request::assignTo)
                .compose(this.memberService::createMember)
                .apply(this.memberService.createMemberId(storeId, request.getId()));
    }

    @GetMapping("stores/{store_id}/members/{member_id}")
    public Optional<Member> getMember(@PathVariable("member_id") String memberId) {
        return this.memberService.getMember(memberId);
    }

    @GetMapping("stores/{store_id}/members")
    public SliceList<Member> getMembers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                        @PathVariable("store_id") String storeId) {
        return this.memberService.getMembers(
                this.memberService.createMemberQuery().toBuilder().page(page).limit(limit).storeId(storeId).build());
    }

    @PatchMapping("/stores/{store_id}/members/{member_id}")
    public Member updateMember(@PathVariable("store_id") String storeId,
                               @PathVariable("member_id") String memberId,
                               @RequestBody MemberRequest request) {
        var member = this.memberService.createMember(
                this.memberService.createMemberId(storeId, memberId));
        return this.memberService.updateMember(request.assignTo(member));
    }
}
