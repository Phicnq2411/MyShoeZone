//package com.philconnal.shoezone.entity.auditable;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.Serializable;
//import java.util.Optional;
//
//@Slf4j
//public class AuditorAwareImpl implements AuditorAware<String>, Serializable {
//    private HttpServletRequest getCurrentHttpRequest() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes instanceof ServletRequestAttributes) {
//            return ((ServletRequestAttributes) requestAttributes).getRequest();
//        }
//        System.out.println("Somthing worng");
//        log.debug("Not called in the context of an HTTP request");
//        return null;
//    }
//
//    @Override
//    @NonNull
//    public Optional<String> getCurrentAuditor() {
//        String key = "hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello";
//
//        String authorization = getCurrentHttpRequest().getHeader("Authorization");
//
//        if (authorization != null) {
//            String token = authorization.replace("Bearer ", "");
//            Jws<Claims> claimsJws = Jwts.parser()
//                    .setSigningKey(key.getBytes())
//                    .parseClaimsJws(token);
//            Claims body = claimsJws.getBody();
//            String username = body.getSubject();
//            return Optional.of(username);
//        }
//        return Optional.of("hajks");
//    }
//}
