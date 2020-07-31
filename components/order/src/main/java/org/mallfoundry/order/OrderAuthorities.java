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

public abstract class OrderAuthorities {

    public static final String ORDER_SEARCH = "order_search";

    public static final String ORDER_READ = "order_read";

    public static final String ORDER_WRITE = "order_write";

    public static final String ORDER_FULFIL = "order_fulfil";

    public static final String ORDER_SIGN = "order_sign";

    public static final String ORDER_RECEIPT = "order_receipt";

    public static final String ORDER_CANCEL = "order_cancel";

    public static final String ORDER_MANAGE = "order_manage";

    public static final String ORDER_SHIPMENT_ADD = "order_shipment_add";

    public static final String ORDER_SHIPMENT_READ = "order_shipment_read";

    public static final String ORDER_SHIPMENT_WRITE = "order_shipment_write";

    public static final String ORDER_SHIPMENT_REMOVE = "order_shipment_remove";

    public static final String ORDER_SHIPMENT_MANAGE = "order_shipment_manage";

    public static final String ORDER_REFUND_APPLY = "order_refund_apply";

    public static final String ORDER_REFUND_READ = "order_refund_read";

    public static final String ORDER_REFUND_CANCEL = "order_refund_cancel";

    public static final String ORDER_REFUND_ACTIVE = "order_refund_active";

    public static final String ORDER_REFUND_APPROVE = "order_refund_approve";

    public static final String ORDER_REFUND_DISAPPROVE = "order_refund_disapprove";

    public static final String ORDER_REFUND_MANAGE = "order_refund_manage";

    public static final String ORDER_REVIEW_ADD = "order_review_add";

    public static final String ORDER_REVIEW_APPROVE = "order_review_approve";

    public static final String ORDER_REVIEW_DISAPPROVE = "order_review_disapprove";

    public static final String ORDER_REVIEW_MANAGE = "order_review_manage";
}
