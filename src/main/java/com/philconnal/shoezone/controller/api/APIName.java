package com.philconnal.shoezone.controller.api;

public interface APIName {

    // Base URL
    String BASE_API_URL = "/api";

    // Authenticate APIs
    String AUTHENTICATE_API = BASE_API_URL + "/auth";
    String AUTHENTICATE_INFO = "/info";
    String AUTHENTICATE_LOGIN = "/login";
    String AUTHENTICATE_LOGOUT = "/logout";

    // User APIs
    String USER_API = BASE_API_URL + "/user";

}
