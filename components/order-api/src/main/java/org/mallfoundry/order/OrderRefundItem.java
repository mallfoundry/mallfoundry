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

    void setItemStatus(ItemStatus itemStatus);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    String getReason();

    void setReason(String reason);

    String getNote();

    void setNote(String note);

    /**
     * 订单退款项凭证图片。
     *
     * @return 图片集合
     */
    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    void itemNotReceive();

    void itemReceive();

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

        Builder amount(BigDecimal amount);

        Builder reason(String reason);

        Builder note(String note);
    }
}
