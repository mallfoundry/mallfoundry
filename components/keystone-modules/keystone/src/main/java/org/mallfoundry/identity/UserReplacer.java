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

package org.mallfoundry.identity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class UserReplacer {

    private static final String EMPTY_STRING = "";

    private static final String[] SEARCHES = new String[]{"{id}", "{phone}", "{email}"};

    public static String replace(String text, User user) {
        return StringUtils.replaceEachRepeatedly(text, SEARCHES, newReplacements(user));
    }

    private static String[] newReplacements(User user) {
        Assert.notNull(user.getId(), message("identity.user.emptyId", "User id must not be null"));
        var mobile = StringUtils.defaultIfEmpty(user.getPhone(), EMPTY_STRING);
        var email = StringUtils.defaultIfEmpty(user.getEmail(), EMPTY_STRING);
        return new String[]{user.getId(), mobile, email};
    }
}
