package com.example.customauthproviderdemo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
   @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=String.valueOf(authentication.getCredentials());
        UserDetails u= userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,u.getPassword())){
            return new UsernamePasswordAuthenticationToken(username,password,u.getAuthorities());
        }else {
            throw new BadCredentialsException("Something went wrong.");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
//      is the same as
//        return UsernamePasswordAuthenticationToken.class
//                .isAssignableFrom(authentication);
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
