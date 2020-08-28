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
import static org.mallfoundry.order.aftersales.OrderDisputeStatus.CANCELLED;
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
    public void addTransaction(OrderDisputeTransaction transaction) {
        this.getTransactions().add(transaction);
    }

    private void addTransaction() {
        var transaction = this.createTransaction(null);
        if (APPLYING.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getAppliedTime());
        } else if (CANCELLED.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getCancelledTime());
        } else if (DISAPPROVED.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getDisapprovedTime());
        } else if (PENDING.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getApprovedTime());
        } else if (SUCCEEDED.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getSucceededTime());
        } else if (FAILED.equals(this.getStatus())) {
            transaction.setCreatedTime(this.getFailedTime());
        }
        this.addTransaction(transaction);
    }

    @Override
    public void apply() throws OrderRefundException {
        if (this.nonIncomplete()) {
            throw OrderExceptions.Refund.applied();
        }
        this.setStatus(APPLYING);
        this.setAppliedTime(new Date());
        this.addTransaction();
    }

    @Override
    public void cancel() throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.notCancel();
        }
        this.setCancelledTime(new Date());
        this.addTransaction();
    }

    @Override
    public void approve() throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setStatus(PENDING);
        this.setApprovedTime(new Date());
        this.addTransaction();
    }

    @Override
    public void disapprove(String disapprovalReason) throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setDisapprovalReason(disapprovalReason);
        this.setStatus(DISAPPROVED);
        this.setDisapprovedTime(new Date());
        this.addTransaction();
    }

    @Override
    public void succeed() throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setStatus(SUCCEEDED);
        this.setSucceededTime(new Date());
        this.addTransaction();
    }

    @Override
    public void fail(String failReason) throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setFailReason(failReason);
        this.setStatus(FAILED);
        this.setFailedTime(new Date());
        this.addTransaction();
    }

}
