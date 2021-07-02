package com.philconnal.shoezone.auth;

import com.philconnal.shoezone.common.exception.errors.MyNotFoundException;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("auth")
public class AuUserServiceIml implements AuthUserService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuUserServiceIml(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthUser selectApplicationUserByUsername(String username) {
        User userByUsername = userService.getUserByUsername(username);
        if (userByUsername == null)
            throw new MyNotFoundException(String.format("User %s not found", username));
        return new AuthUser(
                userByUsername.getUsername(),
                passwordEncoder.encode(userByUsername.getPassword()),
                userByUsername.getRole().getAuthorities(),
                true, userByUsername.isNotLocked(), userByUsername.isActive(), true);
    }
}
