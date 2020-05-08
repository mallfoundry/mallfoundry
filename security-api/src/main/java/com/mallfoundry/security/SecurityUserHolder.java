/*
 * Copyright 2019 the original author or authors.
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
 */

package com.mallfoundry.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Gets the security user in the current context.
 *
 * @author Tang Zhi
 */
public abstract class SecurityUserHolder {

    private static SecurityUser getSecurityUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId() {
        return getSecurityUser().getId();
    }

    public static String getUsername() {
        return getSecurityUser().getUsername();
    }

    public static String getNickname() {
        return getSecurityUser().getNickname();
    }
}
