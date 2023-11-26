package com.example.microservice3.Roles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private static final String SECRET_KEY = "abdelaalylkhdmfqlkjdhfmqdjfqodijdjdalkjnqmdlqmodsijf87674564lkjnmflkjdsqmfh7546536lkjhdfmhsdmfoqd";
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

}
