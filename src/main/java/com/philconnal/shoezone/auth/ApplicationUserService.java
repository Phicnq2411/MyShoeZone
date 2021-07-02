package com.philconnal.shoezone.auth;

import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.LoginAttemptService;
import com.philconnal.shoezone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.philconnal.shoezone.common.constant.UserImplConstant.NO_USER_BY_USERNAME;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final AuthUserService authUserService;
    private final LoginAttemptService loginAttemptService;
    private final UserService userService;

    @Autowired
    public ApplicationUserService(@Qualifier("auth") AuthUserService authUserService,
                                  LoginAttemptService loginAttemptService,
                                  UserService userService
    ) {
        this.authUserService = authUserService;
        this.loginAttemptService = loginAttemptService;
        this.userService = userService;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
//            LOGGER.error(NO_USER_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_BY_USERNAME + username);
        } else {
            try {
                validateLoginAttempt(user);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            user.setLogInDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userService.saveUser(user);

            return authUserService.selectApplicationUserByUsername(username);
        }
    }

    private void validateLoginAttempt(User user) throws ExecutionException {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
                loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
