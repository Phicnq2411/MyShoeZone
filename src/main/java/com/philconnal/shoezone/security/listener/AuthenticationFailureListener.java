package com.philconnal.shoezone.security.listener;

import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFailureListener {
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        System.out.println("failed");
        if (principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            System.out.println(username);
            loginAttemptService.addUserToLoginAttemptCache(username);
        }
    }

}
