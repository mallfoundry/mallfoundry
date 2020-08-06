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

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

import static org.mallfoundry.i18n.Messages.codeKey;

public abstract class MessageHolder {

    private static MessageSourceAccessor messages;

    public static void setMessages(MessageSourceAccessor messages) {
        MessageHolder.messages = messages;
    }

    public static String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    public static String message(String code) {
        return messages.getMessage(codeKey(code));
    }

    public static String message(String code, String defaultMessage) {
        return messages.getMessage(codeKey(code), defaultMessage);
    }

    public static String message(String code, List<?> args) {
        return messages.getMessage(codeKey(code), args.toArray());
    }

    public static String message(String code, List<?> args, String defaultMessage) {
        return messages.getMessage(codeKey(code), args.toArray(), defaultMessage);
    }
}
