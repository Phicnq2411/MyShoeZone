package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    List<User> getAllUsers();

    void saveUser(User user);

    User getUserByEmail(String email);

}
