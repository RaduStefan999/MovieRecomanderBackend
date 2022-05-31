package com.movierecommender.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movierecommender.backend.security.utils.TokenSigner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        }
        catch (IOException exception) {
            throw new AuthenticationException(exception.getMessage()){};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        TokenSigner tokenSigner = new TokenSigner(jwtConfig, secretKey);

        response.addHeader(tokenSigner.getAuthorizationHeader(),
                tokenSigner.getToken(authResult.getName(), authResult.getAuthorities()));
    }
}
