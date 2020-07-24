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

package org.mallfoundry.order;

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单退款项。
 *
 * @author Zhi Tang
 */
public interface OrderRefundItem extends ObjectBuilder.ToBuilder<OrderRefundItem.Builder> {

    String getId();

    void setId(String id);

    String getItemId();

    void setItemId(String itemId);

    ItemStatus getItemStatus();

    void itemNotReceive();

    void itemReceive();

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    String getReason();

    void setReason(String reason);

    String getNotes();

    void setNotes(String notes);

    List<String> getAttachments();

    void setAttachments(List<String> attachments);

    enum ItemStatus {
        /**
         * 未收货。
         */
        NOT_RECEIVED,
        /**
         * 已收货。
         */
        RECEIVED
    }

    interface Builder extends ObjectBuilder<OrderRefundItem> {

        Builder itemId(String itemId);

        Builder itemNotReceive();

        Builder itemReceive();

        Builder itemStatus(ItemStatus status);

        Builder name(String name);

        Builder amount(BigDecimal amount);

        Builder reason(String reason);

        Builder notes(String notes);

        Builder imageUrl(String imageUrl);

        Builder attachments(List<String> attachments);
    }
}
