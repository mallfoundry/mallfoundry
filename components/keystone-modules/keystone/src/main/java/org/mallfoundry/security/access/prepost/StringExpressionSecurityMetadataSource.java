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

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StringExpressionSecurityMetadataSource implements SecurityMetadataSource {

    private final PrePostInvocationAttributeFactory attributeFactory;

    public StringExpressionSecurityMetadataSource(PrePostInvocationAttributeFactory attributeFactory) {
        this.attributeFactory = attributeFactory;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (object instanceof MethodInvocation) {
            MethodInvocation mi = (MethodInvocation) object;
            Object target = mi.getThis();
            if (!(target instanceof StringExpression)) {
                return null;
            }
            var string = (StringExpression) target;
            var attr = this.attributeFactory.createPreInvocationAttribute(null, null, string.getExpression());
            return Objects.isNull(attr) ? Collections.emptyList() : List.of(attr);
        }
        throw new IllegalArgumentException("Object must be a non-null MethodInvocation");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public final boolean supports(Class<?> clazz) {
        return MethodInvocation.class.isAssignableFrom(clazz);
    }
}
