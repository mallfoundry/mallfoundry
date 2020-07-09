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

package org.mallfoundry.security.token;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenAuthenticationConverter authenticationConverter = new AccessTokenAuthenticationConverter();

    private final AuthenticationManager authenticationManager;

    public AccessTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final boolean debug = this.logger.isDebugEnabled();

        try {
            AccessTokenAuthentication authRequest = authenticationConverter.convert(request);
            if (authRequest == null) {
                return;
            }

            String token = authRequest.getName();

            if (debug) {
                this.logger
                        .debug("Token Authentication Authorization header found for token '"
                                + token + "'");
            }

            if (authenticationIsRequired(token)) {
                Authentication authResult = this.authenticationManager
                        .authenticate(authRequest);

                if (debug) {
                    this.logger.debug("Authentication success: " + authResult);
                }

                SecurityContextHolder.getContext().setAuthentication(authResult);
            }

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            if (debug) {
                this.logger.debug("Authentication request for failed: " + failed);
            }
            failed.printStackTrace();
        } finally {
            chain.doFilter(request, response);
        }
    }

    private boolean authenticationIsRequired(String token) {

        Authentication existingAuth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof UsernamePasswordAuthenticationToken
                && !existingAuth.getName().equals(token)) {
            return true;
        }

        return existingAuth instanceof AnonymousAuthenticationToken;
    }

}
