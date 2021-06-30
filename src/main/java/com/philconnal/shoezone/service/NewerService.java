package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.NewUser;
import com.philconnal.shoezone.entity.User;

import java.util.List;

public interface NewerService {
    List<NewUser> getAllUsers();

    void saveUser(NewUser user);


}
