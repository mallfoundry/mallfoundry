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

package org.mallfoundry.payment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {

    PENDING, CAPTURED;

    //    public static final String AUTHORIZED = "Authorized";
//    public static final String CANCELED = "Canceled";
//    public static final String CAPTURED = "Captured";
//    public static final String DECLINED = "Declined";
//    public static final String EXPIRED = "Expired";
//    public static final String PARTIALLY_CAPTURED = "Partially Captured";
//    public static final String PARTIALLY_REFUNDED = "Partially Refunded";
//    public static final String PENDING = "Pending";
//    public static final String REFUNDED = "Refunded";
//    public static final String VOIDED = "Voided";
//    public static final String CARD_VERIFIED = "Card Verified";
//    public static final String CHARGEBACK = "Chargeback";

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }
}
