package org.mallfoundry.security.authentication;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.DefaultSecurityUser;
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
public class MobilePasswordCredentialsAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public MobilePasswordCredentialsAuthenticationProvider(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var mpAuthentication = (MobilePasswordCredentialsAuthenticationToken) authentication;
        String countryCode = mpAuthentication.getCountryCode();
        String mobile = mpAuthentication.getMobile();
        String password = mpAuthentication.getPassword();
        var user = this.userService.getUserByMobile(countryCode, mobile)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The mobile %s%s not found", countryCode, mobile)));
        var securityUser = new DefaultSecurityUser(user);
        this.authenticationChecks(securityUser, mpAuthentication);
        return new UsernamePasswordAuthenticationToken(securityUser, password, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePasswordCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected void authenticationChecks(UserDetails userDetails,
                                        MobilePasswordCredentialsAuthenticationToken authentication) throws AuthenticationException {
        String presentedPassword = authentication.getPassword();
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
