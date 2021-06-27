package com.philconnal.shoezone.auth;

public interface AuthUserService {

    AuthUser selectApplicationUserByUsername(String username);

}
