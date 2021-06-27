package com.philconnal.shoezone.controller;


import com.philconnal.shoezone.auth.AuthUser;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.request.AuthUserRequest;
import com.philconnal.shoezone.controller.response.AuthResponse;
import com.philconnal.shoezone.util.ResponseUtil;
import com.philconnal.shoezone.controller.response.RestApiResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping(APIName.AUTHENTICATE_API)
public class AuthController {

    private final ResponseUtil responseUtil;

    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthController(ResponseUtil responseUtil, AuthenticationManager authenticationManager) {
        this.responseUtil = responseUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping()
    public ResponseEntity<RestApiResponse> authenticateUser(@Valid @RequestBody AuthUserRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (MyBadRequestException e) {
            throw new RuntimeException(e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String key = "hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello";

        AuthUser principal = (AuthUser) authentication.getPrincipal();

        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("authorities", principal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();

        AuthResponse authResponse = new AuthResponse();

        authResponse.setToken("Bearer " + token);
        authResponse.setUsername(principal.getUsername());
        return responseUtil.successResponse(authResponse);
    }
}
