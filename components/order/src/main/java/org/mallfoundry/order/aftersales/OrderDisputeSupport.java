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

package org.mallfoundry.order.aftersales;

import org.mallfoundry.order.OrderExceptions;

import java.util.Date;

import static org.mallfoundry.order.aftersales.OrderDisputeStatus.APPLYING;
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.DISAPPROVED;
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.FAILED;
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.INCOMPLETE;
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.PENDING;
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.SUCCEEDED;

public abstract class OrderDisputeSupport implements MutableOrderDispute {

    @Override
    public void itemNotReceive() {
        this.setItemStatus(ItemStatus.NOT_RECEIVED);
    }

    @Override
    public void itemReceive() {
        this.setItemStatus(ItemStatus.RECEIVED);
    }
    public boolean nonIncomplete() {
        return !INCOMPLETE.equals(this.getStatus());
    }

    public boolean nonApplying() {
        return !APPLYING.equals(this.getStatus());
    }

    public boolean nonPending() {
        return !PENDING.equals(this.getStatus());
    }

    @Override
    public void apply() throws OrderRefundException {
        if (this.nonIncomplete()) {
            throw OrderExceptions.Refund.applied();
        }
        this.setStatus(APPLYING);
        this.setAppliedTime(new Date());
    }

    @Override
    public void cancel() throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.notCancel();
        }
    }

    @Override
    public void approve() throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setStatus(PENDING);
        this.setApprovedTime(new Date());
    }

    @Override
    public void disapprove(String disapprovalReason) throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setDisapprovalReason(disapprovalReason);
        this.setStatus(DISAPPROVED);
        this.setDisapprovedTime(new Date());
    }

    @Override
    public void succeed() throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setStatus(SUCCEEDED);
        this.setSucceededTime(new Date());
    }

    @Override
    public void fail(String failReason) throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setFailReason(failReason);
        this.setStatus(FAILED);
        this.setFailedTime(new Date());
    }

}
