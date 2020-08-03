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

package org.mallfoundry.security.access;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DelegatingSecurityMetadataSource implements MethodSecurityMetadataSource {

    private static final List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();

    private final List<SecurityMetadataSource> securityMetadataSources;

    public DelegatingSecurityMetadataSource(List<SecurityMetadataSource> securityMetadataSources) {
        Assert.notNull(securityMetadataSources, "SecurityMetadataSource cannot be null");
        this.securityMetadataSources = securityMetadataSources;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        for (SecurityMetadataSource s : this.securityMetadataSources) {
            var attributes = s.getAttributes(object);
            if (attributes != null && !attributes.isEmpty()) {
                return attributes;
            }
        }
        return NULL_CONFIG_ATTRIBUTE;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> attributes = new HashSet<>();
        for (SecurityMetadataSource s : this.securityMetadataSources) {
            Collection<ConfigAttribute> attrs = s.getAllConfigAttributes();
            if (attrs != null) {
                attributes.addAll(attrs);
            }
        }
        return attributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        for (SecurityMetadataSource source : this.securityMetadataSources) {
            if (source instanceof MethodSecurityMetadataSource) {
                var ms = (MethodSecurityMetadataSource) source;
                var attributes = ms.getAttributes(method, targetClass);
                if (attributes != null && !attributes.isEmpty()) {
                    return attributes;
                }
            }
        }
        return NULL_CONFIG_ATTRIBUTE;
    }
}
