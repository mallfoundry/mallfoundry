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

package org.mallfoundry.test;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mallfoundry.TestMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.util.List;

/**
 * 无需 Spring 时的测试环境。
 *
 * @author Zhi Tang
 */
public class StaticExtension implements BeforeAllCallback {

    private static final Logger logger = LoggerFactory.getLogger(StaticExtension.class);

    private static final String BASE_PACKAGE_NAME = TestMarker.class.getPackageName();

    private static final String MESSAGE_HOLDER_CLASS_NAME = BASE_PACKAGE_NAME + ".i18n.MessageHolder";

    private MessageSourceAccessor createMessageSourceAccessor() {
        var basenames = List.of("order", "payment")
                .stream()
                .map(basename -> String.format("%s.%s.messages", BASE_PACKAGE_NAME, basename))
                .toArray(String[]::new);
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basenames);
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setAlwaysUseMessageFormat(true);
        return new MessageSourceAccessor(messageSource);
    }

    private void setHolderMessages() {
        try {
            var holderClass = Class.forName(MESSAGE_HOLDER_CLASS_NAME);
            var method = ReflectionUtils.findMethod(holderClass, "setMessages", MessageSourceAccessor.class);
            Assert.notNull(method, "The MessageHolder's setMessages method does not exist");
            ReflectionUtils.invokeMethod(method, null, this.createMessageSourceAccessor());
            if (logger.isDebugEnabled()) {
                logger.debug("MessageHolder successfully set up messages");
            }
        } catch (ClassNotFoundException ex) {
            if (logger.isErrorEnabled()) {
                logger.error("MessageHolder class not found");
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        this.setHolderMessages();
    }
}
