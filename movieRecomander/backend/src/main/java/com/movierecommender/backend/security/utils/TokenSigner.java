package com.movierecommender.backend.security.utils;

import com.movierecommender.backend.security.config.UserRoles;
import com.movierecommender.backend.security.jwt.JwtConfig;
import com.movierecommender.backend.users.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class TokenSigner {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public TokenSigner(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    public String getToken(User user) {
        return this.computeToken(user.getEmail(), UserRoles.valueOf(user.getRole()).getGrantedAuthorities());
    }

    public String getToken(String name, Collection<? extends GrantedAuthority> authorities) {
        return this.computeToken(name, authorities);
    }

    public String computeToken(String name, Collection<? extends GrantedAuthority> authorities) {
        return  jwtConfig.getTokenPrefix() + Jwts.builder()
                .setSubject(name)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
    }

    public String getAuthorizationHeader() {
        return jwtConfig.getAuthorizationHeader();
    }
}
