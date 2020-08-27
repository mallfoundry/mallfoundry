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

import com.fasterxml.jackson.annotation.JsonValue;
import org.mallfoundry.customer.CustomerOwnership;
import org.mallfoundry.store.StoreOwnership;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDispute extends StoreOwnership, CustomerOwnership {

    String getId();

    void setId(String id);

    String getOrderId();

    String getApplicant();

    void setApplicant(String applicant);

    String getApplicantId();

    void setApplicantId(String applicantId);

    DisputeKind getKind();

    void setKind(DisputeKind kind);

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

    // 订单项金额
    BigDecimal getItemAmount();

    void setItemAmount(BigDecimal itemAmount);

    // 订单退款项金额
    BigDecimal getAmount();

    void setAmount(BigDecimal refundAmount);

    String getNotes();

    void setNotes(String notes);

    List<String> getAttachments();

    void setAttachments(List<String> attachments);

    enum DisputeKind {
        EXCHANGE /* 换货 */,
        ONLY_REFUND /* 仅退款 */,
        RETURN_REFUND /* 退货退款 */;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
