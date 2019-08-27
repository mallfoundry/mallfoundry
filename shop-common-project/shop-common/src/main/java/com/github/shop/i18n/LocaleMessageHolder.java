/*
 * Copyright $year the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.shop.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

public class LocaleMessageHolder {

    private static final ThreadLocal<Locale> localeHolder = new ThreadLocal<>();

    /**
     * Shared default locale.
     */
    private static Locale defaultLocale = Locale.getDefault();

    private static MessageSource messageSource;

    public static void setLocale(Locale locale) {
        localeHolder.set(locale);
    }

    /**
     * Return the Locale associated with the given user context, if any,
     * or the system default Locale otherwise. This is effectively a
     * replacement for {@link Locale#getDefault()},
     * able to optionally respect a user-level Locale setting.
     *
     * @return the current Locale, or the system default Locale if no
     * specific Locale has been associated with the current thread
     */
    private static Locale getLocale() {
        Locale locale = localeHolder.get();
        return Objects.isNull(locale) ? defaultLocale : locale;
    }

    /**
     * Reset the Locale for the current thread.
     */
    public static void resetLocale() {
        localeHolder.remove();
    }

    private static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource source) {
        messageSource = source;
    }

    public static String getMessage(String code, Object... args) {
        return getMessageSource().getMessage(code, args, getLocale());
    }
}
