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

package org.mallfoundry.store;

import org.mallfoundry.order.OrdersPlacedEvent;
import org.mallfoundry.member.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class MemberAutoJoinEventListener {

    private final MemberService memberService;

    public MemberAutoJoinEventListener(MemberService memberService) {
        this.memberService = memberService;
    }

    @EventListener
    public void onOrdersPlaced(OrdersPlacedEvent event) {
        var orders = event.getOrders();
        for (var order : orders) {
            var memberId = this.memberService.createMemberId(order.getStoreId(), order.getCustomerId());
            var member = this.memberService.createMember(memberId);
            this.memberService.joinMember(member);
        }
    }
}
