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

package org.mallfoundry.autoconfigure.member;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.member.DefaultMemberService;
import org.mallfoundry.member.MemberProcessor;
import org.mallfoundry.member.MemberRepository;
import org.mallfoundry.member.MemberService;
import org.mallfoundry.member.lifecycle.StoreMemberLifecycle;
import org.mallfoundry.member.repository.jpa.DelegatingJpaMemberRepository;
import org.mallfoundry.member.repository.jpa.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class MemberAutoConfiguration {

    @Bean
    public DelegatingJpaMemberRepository delegatingJpaMemberRepository(JpaMemberRepository repository) {
        return new DelegatingJpaMemberRepository(repository);
    }

    @Bean
    public DefaultMemberService defaultMemberService(@Autowired(required = false)
                                                     @Lazy List<MemberProcessor> processors,
                                                     UserService userService,
                                                     MemberRepository repository) {
        var service = new DefaultMemberService(userService, repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public StoreMemberLifecycle storeMemberLifecycle(MemberService memberService) {
        return new StoreMemberLifecycle(memberService);
    }
}
