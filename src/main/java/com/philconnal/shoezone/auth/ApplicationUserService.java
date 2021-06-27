package com.philconnal.shoezone.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final AuthUserService authUserService;

    @Autowired
    public ApplicationUserService(@Qualifier("auth") AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserService
                .selectApplicationUserByUsername(username);
    }
}
