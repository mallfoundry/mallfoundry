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

package org.mallfoundry.security.access;

import java.io.Serializable;

/**
 * 访问控制列表中所表示的资源对象是一种受限资源对象。
 * 将一个权限对一种受限资源对象授权给一个主体后，这个主体才有权访问这个资源对象。
 *
 * @author Zhi Tang
 * @see Principal
 */
public interface Resource extends Serializable {

    String STORE_TYPE = "Store";

    String PRODUCT_TYPE = "Product";

    String CUSTOMER_TYPE = "Customer";

    String ORDER_TYPE = "Order";

    String getIdentifier();

    String getType();
}
