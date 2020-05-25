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
