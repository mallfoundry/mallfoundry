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

package org.mallfoundry.security.access.prepost;

import lombok.Getter;
import org.aopalliance.intercept.MethodInvocation;
import org.mallfoundry.security.access.AccessDeniedException;
import org.mallfoundry.security.access.AuthorizeHolderStrategy;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class StringExpressionAuthorizeHolderStrategy implements AuthorizeHolderStrategy {

    private final MethodSecurityInterceptor securityInterceptor;

    public StringExpressionAuthorizeHolderStrategy(MethodSecurityInterceptor interceptor) {
        this.securityInterceptor = Objects.requireNonNull(interceptor, "Only non-null MethodSecurityInterceptor instances are permitted");
    }

    @Override
    public void authorize(String expression) throws AccessDeniedException {
        var invocation = new StringExpressionMethodInvocation(expression);
        try {
            this.securityInterceptor.invoke(invocation);
        } catch (Throwable t) {
            throw new AccessDeniedException(t);
        }
    }

    private static class StringExpressionMethodInvocation implements MethodInvocation {
        @Getter
        private final Method method;
        @Getter
        private final Object[] arguments;
        private final Object targetObject;

        StringExpressionMethodInvocation(String expression) {
            var string = new StringExpression(expression);
            this.targetObject = string;
            this.method = ReflectionUtils.findMethod(StringExpression.class, "authorize");
            this.arguments = string.getArguments() == null ? new Object[0] : string.getArguments();
        }

        public AccessibleObject getStaticPart() {
            return this.method;
        }

        public Object getThis() {
            return targetObject;
        }

        public Object proceed() throws InvocationTargetException, IllegalAccessException {
            return method.invoke(this.getThis(), this.arguments);
        }
    }
}
