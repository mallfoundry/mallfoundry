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

package org.mallfoundry.catalog.product;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class ProductMessages {

    private static final String PRODUCT_VARIANT_NOT_FOUND_MESSAGE_CODE_KEY = "catalog.product.ProductVariant.notFound";

    private static final String PRODUCT_OPTION_NOT_FOUND_MESSAGE_CODE_KEY = "catalog.product.ProductOption.notFound";

    private static final String PRODUCT_VARIANT_NOT_FOUND_DEFAULT_MESSAGE = "Product variant does not exist";

    private static final String PRODUCT_OPTION_NOT_FOUND_DEFAULT_MESSAGE = "Product option does not exist";

    public abstract static class Variant {
        public static String notFound() {
            return message(PRODUCT_VARIANT_NOT_FOUND_MESSAGE_CODE_KEY, PRODUCT_VARIANT_NOT_FOUND_DEFAULT_MESSAGE);
        }
    }

    public abstract static class Option {
        public static String notFound() {
            return message(PRODUCT_OPTION_NOT_FOUND_MESSAGE_CODE_KEY, PRODUCT_OPTION_NOT_FOUND_DEFAULT_MESSAGE);
        }
    }
}
