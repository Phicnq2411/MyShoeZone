package com.philconnal.shoezone.jwt;

import com.google.common.base.Strings;
import com.philconnal.shoezone.auth.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final String KEY = "hellohellohellohellohellohellohellohellohello" +
            "hellohellohellohellohellohellohellohellohellohello";

    private Claims getBody(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                    .parseClaimsJws(token);
            return claimsJws.getBody();
    }

    public String getUnAuthorizationToken(String authorizationHeard) {
        return authorizationHeard.replace("Bearer ", "");
    }

    public String getUsername(String authorizationHeader) {
        return getBody(authorizationHeader).getSubject();
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(String authorizationHeader) {
        var authorities = (List<Map<String, String>>) getBody(authorizationHeader).get("authorities");

        return authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                .collect(Collectors.toSet());
    }

    public String generateToken(AuthUser authUser) {

        String token = Jwts.builder()
                .setSubject(authUser.getUsername())
                .claim("authorities", authUser.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
        return "Bearer " + token;
    }

    boolean validateToken(String authorizationHeader) {
        return !Strings.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ");
    }
}
