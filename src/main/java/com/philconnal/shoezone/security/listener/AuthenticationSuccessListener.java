package com.philconnal.shoezone.security.listener;

import com.philconnal.shoezone.auth.AuthUser;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class AuthenticationSuccessListener {
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AbstractAuthenticationEvent event) throws ExecutionException {
        System.out.println("Succecfull");
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof AuthUser) {
            System.out.println("Auth");
            AuthUser user = (AuthUser) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
