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

package org.mallfoundry.security;


import org.mallfoundry.security.access.AccessControlAuthorizer;
import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.AccessControlPermissionEvaluator;
import org.mallfoundry.security.access.AuthorizeHolder;
import org.mallfoundry.security.access.DelegatingSecurityMetadataSource;
import org.mallfoundry.security.access.prepost.StringExpressionAuthorizeHolderStrategy;
import org.mallfoundry.security.access.prepost.StringExpressionSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.List;

@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Configuration
public class SecurityAccessControlConfiguration extends GlobalMethodSecurityConfiguration {

    @Bean
    public AccessControlPermissionEvaluator accessControlPermissionEvaluator(AccessControlManager manager,
                                                                             AccessControlAuthorizer authorizer) {
        return new AccessControlPermissionEvaluator(manager, authorizer);
    }

    @Bean
    public StringExpressionSecurityMetadataSource stringExpressionSecurityMetadataSource() {
        var attributeFactory = new ExpressionBasedAnnotationAttributeFactory(getExpressionHandler());
        return new StringExpressionSecurityMetadataSource(attributeFactory);
    }

    @Override
    public MethodSecurityMetadataSource methodSecurityMetadataSource() {
        var sources = super.methodSecurityMetadataSource();
        return new DelegatingSecurityMetadataSource(List.of(this.stringExpressionSecurityMetadataSource(),
                sources));
    }

    @Bean
    @Autowired(required = false)
    public StringExpressionAuthorizeHolderStrategy stringExpressionAuthorizeHolderStrategy(@Lazy MethodSecurityInterceptor interceptor) {
        var strategy = new StringExpressionAuthorizeHolderStrategy(interceptor);
        AuthorizeHolder.setHolderStrategy(strategy);
        return strategy;
    }
}
