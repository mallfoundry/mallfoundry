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

package org.mallfoundry.order.dispute;

import org.mallfoundry.data.SliceList;
import org.springframework.beans.BeanUtils;

public class DefaultOrderDisputeService implements OrderDisputeService {

    private final OrderDisputeRepository orderDisputeRepository;

    public DefaultOrderDisputeService(OrderDisputeRepository orderDisputeRepository) {
        this.orderDisputeRepository = orderDisputeRepository;
    }

    @Override
    public OrderDisputeQuery createOrderDisputeQuery() {
        return new DefaultOrderDisputeQuery();
    }

    public SliceList<OrderDispute> getOrderDisputes(OrderDisputeQuery query) {
        return this.orderDisputeRepository.findAll(query);
    }

    public long countOrderDisputes(OrderDisputeQuery query) {
        return this.orderDisputeRepository.count(query);
    }

    public void saveOrderDispute(OrderRefund refund) {
        var dispute = this.orderDisputeRepository.create(refund.getId());
        BeanUtils.copyProperties(refund, dispute);
        this.orderDisputeRepository.save(dispute);
    }
}
