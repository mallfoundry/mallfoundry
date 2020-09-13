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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderItem extends ObjectBuilder.ToBuilder<OrderItem.Builder> {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getName();

    void setName(String name);

    int getQuantity();

    void setQuantity(int quantity);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getShippingCost();

    void setShippingCost(BigDecimal shippingCost);

    BigDecimal getDiscountShippingCost();

    void discountShippingCost(BigDecimal discountShippingCost);

    BigDecimal getDiscountTotalPrice();

    void discountTotalPrice(BigDecimal discountTotalPrice);
/*
    BigDecimal getCouponDiscountAmount();

    void couponDiscountAmount(BigDecimal couponAmount);*/

    BigDecimal getTotalPrice();

    void applyRefund(BigDecimal refundAmount);

    void cancelRefund(BigDecimal failAmount);

    void succeedRefund(BigDecimal succeedAmount);

    void failRefund(BigDecimal failAmount);

    BigDecimal getRefundedAmount();

    BigDecimal getRefundingAmount();

    boolean isReviewed();

    void review();

    boolean isShipped();

    Date getShippedTime();

    void ship();

    BigDecimal getSubtotalAmount();

    BigDecimal getTotalAmount();

    interface Builder extends ObjectBuilder<OrderItem> {

        Builder id(String id);

        Builder storeId(String storeId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder imageUrl(String imageUrl);

        Builder name(String name);

        Builder quantity(int quantity);

        Builder price(BigDecimal price);

        Builder shippingCost(BigDecimal shippingCost);
    }
}
