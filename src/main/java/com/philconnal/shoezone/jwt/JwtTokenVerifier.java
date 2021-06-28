package com.philconnal.shoezone.jwt;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Slf4j
public class JwtTokenVerifier extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (!jwtTokenProvider.validateToken(authorizationHeader)) {
            System.out.println(jwtTokenProvider.validateToken(authorizationHeader));
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String username = jwtTokenProvider.getUsername(authorizationHeader);
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = jwtTokenProvider.getGrantedAuthorities(authorizationHeader);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Verifier");
        } catch (
                JwtException e) {
            throw new IllegalStateException(
                    String.format("Token %s cannot be trusted",
                            jwtTokenProvider.getUnAuthorizationToken(authorizationHeader)));
        }

        filterChain.doFilter(request, response);
    }

}
