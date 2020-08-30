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

import com.fasterxml.jackson.annotation.JsonValue;

public interface UserPasswordReset extends TenantOwnership {

    String getUsername();

    String getPassword();

    /**
     * 设置新密码。
     *
     * @param password 新密码
     */
    void setPassword(String password);

    String getOriginalPassword();

    /**
     * 在使用 用户名、手机号码、邮箱地址修改密码时，需要设置原始密码。
     *
     * @param originalPassword 原始密码
     */
    void setOriginalPassword(String originalPassword);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getPhone();

    void setPhone(String phone);

    String getEmail();

    void setEmail(String email);

    String getCaptchaToken();

    void setCaptchaToken(String captchaToken);

    String getCaptchaCode();

    void setCaptchaCode(String captchaCode);

    Mode getMode();

    void setMode(Mode mode);

    enum Mode {
        USERNAME_PASSWORD,
        EMAIL_PASSWORD,
        PHONE_PASSWORD,
        CAPTCHA;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
