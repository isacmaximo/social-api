package com.social.api.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.social.api.auth.dto.JwtTokenDto;
import com.social.api.auth.dto.UserRequestDto;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(Authentication authentication){
        Authentication authenticatedUser = authenticationManager.authenticate(authentication);
        return jwtService.generateToken(authenticatedUser);
    }

    public JwtTokenDto register(UserRequestDto request){
       userService.createUser(request);
       JwtTokenDto jwtTokenDto = new JwtTokenDto();
       jwtTokenDto.setToken(jwtService.generateToken(
          new UsernamePasswordAuthenticationToken(
            request.getEmail(), request.getPassword()
          )
       ));
       return jwtTokenDto;
    }

    public String refreshToken(String refreshToken){
        return jwtService.generateRefreshToken(refreshToken);
    }
}
