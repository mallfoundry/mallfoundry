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

package org.mallfoundry.trade;

import java.util.Date;

import static org.mallfoundry.trade.TransactionStatus.CANCELED;
import static org.mallfoundry.trade.TransactionStatus.CAPTURED;
import static org.mallfoundry.trade.TransactionStatus.COMPLETED;
import static org.mallfoundry.trade.TransactionStatus.EXPIRED;
import static org.mallfoundry.trade.TransactionStatus.FAILED;
import static org.mallfoundry.trade.TransactionStatus.PENDING;

public abstract class TransactionSupport implements MutableTransaction {

    @Override
    public void create(TransactionType type) {
        this.setStatus(PENDING);
        this.setType(type);
        this.setCreatedTime(new Date());
    }

    @Override
    public void capture() {
        this.setStatus(CAPTURED);
    }

    @Override
    public void fail() {
        this.setStatus(FAILED);
    }

    @Override
    public void expire() {
        this.setStatus(EXPIRED);
    }

    @Override
    public void cancel() {
        this.setStatus(CANCELED);
    }

    @Override
    public void complete() {
        this.setStatus(COMPLETED);
    }
}
