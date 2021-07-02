package com.philconnal.shoezone.controller;


import com.philconnal.shoezone.auth.AuthUser;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.request.AuthUserRequest;
import com.philconnal.shoezone.controller.response.AuthResponse;
import com.philconnal.shoezone.controller.response.RestApiResponse;
import com.philconnal.shoezone.jwt.JwtTokenProvider;
import com.philconnal.shoezone.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(APIName.AUTHENTICATE_API)
public class AuthController {

    private final ResponseUtil responseUtil;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthController(ResponseUtil responseUtil, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.responseUtil = responseUtil;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<RestApiResponse> authenticateUser(@Valid @RequestBody AuthUserRequest loginRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);


            AuthUser principal = (AuthUser) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(principal);

            AuthResponse authResponse = new AuthResponse();

            authResponse.setToken(token);
            authResponse.setUsername(principal.getUsername());

            return responseUtil.successResponse(authResponse);
        } catch (MyBadRequestException e) {
            throw new RuntimeException(e);
        }
    }
}
