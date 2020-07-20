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

import org.mallfoundry.security.authentication.CaptchaCredentialsAuthenticationProvider;
import org.mallfoundry.security.authentication.MobilePasswordCredentialsAuthenticationProvider;
import org.mallfoundry.security.token.AccessTokenAuthenticationFilter;
import org.mallfoundry.security.token.AccessTokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityUserService securityUserService;

    private final AccessTokenAuthenticationProvider tokenAuthenticationProvider;

    private final CaptchaCredentialsAuthenticationProvider captchaCredentialsAuthenticationProvider;

    private final MobilePasswordCredentialsAuthenticationProvider mobilePasswordCredentialsAuthenticationProvider;

    public SecurityConfiguration(SecurityUserService securityUserService,
                                 AccessTokenAuthenticationProvider tokenAuthenticationProvider,
                                 CaptchaCredentialsAuthenticationProvider captchaCredentialsAuthenticationProvider,
                                 MobilePasswordCredentialsAuthenticationProvider mobilePasswordCredentialsAuthenticationProvider) {
        this.securityUserService = securityUserService;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.captchaCredentialsAuthenticationProvider = captchaCredentialsAuthenticationProvider;
        this.mobilePasswordCredentialsAuthenticationProvider = mobilePasswordCredentialsAuthenticationProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.securityUserService);
//        auth.
        auth.authenticationProvider(this.tokenAuthenticationProvider)
                .authenticationProvider(this.captchaCredentialsAuthenticationProvider)
                .authenticationProvider(this.mobilePasswordCredentialsAuthenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
        http.cors();
//        http.formLogin();
        http.addFilterAfter(new AccessTokenAuthenticationFilter(this.authenticationManager()), BasicAuthenticationFilter.class);
    }


}
