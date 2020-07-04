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

package org.mallfoundry.security;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Gets the security user in the current context.
 *
 * @author Zhi Tang
 */
public abstract class SubjectHolder {

//    private static SubjectAuthorizer authorizer;

    public static boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) && authentication.isAuthenticated();
    }

    private static Subject requirePrincipal() {
        if (!isAuthenticated()) {
            throw new SecurityException();
        }
        return (Subject) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId() {
        return requirePrincipal().getId();
    }

    public static String getUsername() {
        return requirePrincipal().getUsername();
    }

    public static String getNickname() {
        return requirePrincipal().getNickname();
    }
/*
    public static void checkPermission(Resource resource, String permission) {

    }*/
}
