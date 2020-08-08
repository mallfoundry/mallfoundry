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

package org.mallfoundry.security.authentication;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.UserDetailsSubject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PhonePasswordCredentialsAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public PhonePasswordCredentialsAuthenticationProvider(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var ppAuthentication = (PhonePasswordCredentialsAuthenticationToken) authentication;
        String countryCode = ppAuthentication.getCountryCode();
        String phone = ppAuthentication.getPhone();
        String password = ppAuthentication.getPassword();
        var user = this.userService.getUserByPhone(countryCode, phone)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The phone %s%s not found", countryCode, phone)));
        var securityUser = new UserDetailsSubject(user);
        this.authenticationChecks(securityUser, ppAuthentication);
        return new UsernamePasswordAuthenticationToken(securityUser, password, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhonePasswordCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected void authenticationChecks(UserDetails userDetails,
                                        PhonePasswordCredentialsAuthenticationToken authentication) throws AuthenticationException {
        String presentedPassword = authentication.getPassword();
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
