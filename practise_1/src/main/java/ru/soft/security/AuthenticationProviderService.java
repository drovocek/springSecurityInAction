package ru.soft.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.soft.security.data.CustomUserDetails;
import ru.soft.security.data.JpaUserDetailsService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {

    public static final String BAD_CRED_EXC_TEXT = "Bad credentials";
    private final JpaUserDetailsService userDetailsService;
    private final PasswordEncoder delegatingPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authenticate '{}'", authentication);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        validatePassword(password, userDetails.getPassword());

        log.info("authenticate - SUCCESS");
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    private void validatePassword(String rawPassword, String password) {
        if (!this.delegatingPasswordEncoder.matches(rawPassword, password)) {
            log.info("authenticate - ERROR");
            throw new BadCredentialsException(BAD_CRED_EXC_TEXT);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}