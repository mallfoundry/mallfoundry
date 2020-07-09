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

package org.mallfoundry.captcha;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Captcha {

    String MOBILE_PARAMETER_NAME = "mobile";

    String COUNTRY_CODE_PARAMETER_NAME = "country_code";

    CaptchaType getType();

    String getToken();

    void setToken(String token);

    String getCode();

    void setCode(String code);

    boolean checkCode(String code) throws CaptchaException;

    Map<String, String> getParameters();

    String getParameter(String name);

    void setParameters(Map<String, String> parameters);

    int getIntervals();

    void setIntervals(int intervals);

    int getExpires();

    void setExpires(int expires);

    Date getCreatedTime();

    void setCreatedTime(Date createdTime);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Captcha> {

        Builder token(String token);

        Builder code(String code);

        Builder parameters(Map<String, String> parameters);

        Builder expires(int expires);

        Builder intervals(int intervals);

        Builder createdTime(Date createdTime);
    }

    class BuilderSupport implements Builder {

        private final Captcha captcha;

        public BuilderSupport(Captcha captcha) {
            this.captcha = captcha;
        }

        @Override
        public Builder token(String token) {
            this.captcha.setToken(token);
            return this;
        }

        @Override
        public Builder code(String code) {
            this.captcha.setCode(code);
            return this;
        }

        @Override
        public Builder parameters(Map<String, String> parameters) {
            this.captcha.setParameters(new HashMap<>(parameters));
            return this;
        }

        @Override
        public Builder expires(int expires) {
            this.captcha.setExpires(expires);
            return this;
        }

        @Override
        public Builder intervals(int intervals) {
            this.captcha.setIntervals(intervals);
            return this;
        }

        @Override
        public Builder createdTime(Date createdTime) {
            this.captcha.setCreatedTime(createdTime);
            return this;
        }

        @Override
        public Captcha build() {
            return this.captcha;
        }
    }

}
