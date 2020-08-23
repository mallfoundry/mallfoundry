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

import com.fasterxml.jackson.annotation.JsonValue;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderRefund extends ObjectBuilder.ToBuilder<OrderRefund.Builder> {

    String getId();

    void setId(String id);

    String getOrderId();

    String getItemId();

    void setItemId(String itemId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    RefundKind getKind();

    void setKind(RefundKind kind);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    ItemStatus getItemStatus();

    void itemNotReceive();

    void itemReceive();

    String getNotes();

    void setNotes(String notes);

    List<String> getAttachments();

    void setAttachments(List<String> attachments);

    RefundStatus getStatus();

    String getReason();

    void setReason(String reason);

    /**
     * 申请退款。
     */
    void apply() throws OrderRefundException;


    Date getAppliedTime();

    /**
     * 取消退款申请。
     */
    void cancel() throws OrderRefundException;

    /**
     * 同意退款。
     */
    void approve() throws OrderRefundException;

    Date getApprovedTime();

    void disapprove(String disapprovalReason) throws OrderRefundException;

    String getDisapprovalReason();

    Date getDisapprovedTime();

    /**
     * 退款成功。
     */
    void succeed() throws OrderRefundException;

    Date getSucceededTime();

    /**
     * 退款失败。
     */
    void fail(String failReason) throws OrderRefundException;

    String getFailReason();

    Date getFailedTime();

    enum ItemStatus {
        /**
         * 未收货。
         */
        NOT_RECEIVED,
        /**
         * 已收货。
         */
        RECEIVED;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    enum RefundKind {
        ONLY_REFUND /* 仅退款 */,
        RETURN_REFUND /* 退货退款 */;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    enum RefundStatus {
        INCOMPLETE /* 空状态 */,
        APPLYING /* 买家申请退款 */,
        CANCELLED, /* 取消申请退款 */
        DISAPPROVED /* 退款未批准 */,
        PENDING /* 退款中 */,
        SUCCEEDED  /* 退款成功 */,
        FAILED  /* 退款失败 */;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    interface Builder extends ObjectBuilder<OrderRefund> {

        Builder itemStatus(ItemStatus status);

        Builder itemId(String itemId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder kind(RefundKind kind);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder amount(BigDecimal amount);

        Builder reason(String reason);

        Builder notes(String notes);

        Builder attachments(List<String> attachments);

        Builder disapprovalReason(String disapprovalReason);

        Builder failReason(String failReason);
    }
}
