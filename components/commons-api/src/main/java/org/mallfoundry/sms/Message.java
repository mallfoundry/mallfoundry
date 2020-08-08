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

package org.mallfoundry.sms;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Message extends Serializable {

    String CODE_VARIABLE_NAME = "code";

    String getTemplate();

    void setTemplate(String template);

    String getSignature();

    void setSignature(String signature);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getPhone();

    void setPhone(String phone);

    String getBody();

    void setBody(String body);

    Map<String, String> getVariables();

    void setVariable(String name, String value);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Message> {

        Builder countryCode(String countryCode);

        Builder phone(String phone);

        Builder template(String template);

        Builder signature(String signature);

        Builder body(String body);

        Builder variable(String name, String value);

    }

    abstract class BuilderSupport implements Builder {

        private final Message message;

        public BuilderSupport(Message message) {
            this.message = message;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.message.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder phone(String phone) {
            this.message.setPhone(phone);
            return this;
        }

        @Override
        public Builder template(String template) {
            this.message.setTemplate(template);
            return this;
        }

        @Override
        public Builder signature(String signature) {
            this.message.setSignature(signature);
            return this;
        }

        @Override
        public Builder body(String body) {
            this.message.setBody(body);
            return this;
        }

        @Override
        public Builder variable(String name, String value) {
            this.message.setVariable(name, value);
            return this;
        }

        @Override
        public Message build() {
            return this.message;
        }
    }
}
