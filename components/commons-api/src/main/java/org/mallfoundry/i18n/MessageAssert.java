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

package org.mallfoundry.i18n;

import org.springframework.util.Assert;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class MessageAssert {

    private static final String NOT_BLANK_MESSAGE = "constraints.NotBlank.message";

    private static final String NOT_EMPTY_MESSAGE = "constraints.NotEmpty.message";

    private static String message(String codeKey, String fieldName, String defaultMessage) {
        return MessageHolder.message(codeKey, List.of(fieldName), defaultMessage);
    }

    public static void notBlank(String propertyName, String text) {
        Assert.isTrue(isNotBlank(text), message(NOT_BLANK_MESSAGE, propertyName, String.format("%s must not be blank", propertyName)));
    }

    public static void notEmpty(String propertyName, String text) {
        Assert.isTrue(isNotEmpty(text), message(NOT_EMPTY_MESSAGE, propertyName, String.format("%s must not be empty", propertyName)));
    }

}
