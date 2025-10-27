package com.social.api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.api.auth.dto.JwtTokenDto;
import com.social.api.auth.dto.UserRequestDto;
import com.social.api.auth.service.AuthenticationService;

@RestController
@RequestMapping("auth")
public class AuthenticateController {
    private final AuthenticationService authenticationService;

    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ResponseEntity<JwtTokenDto> login(@RequestBody UserRequestDto request){
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setToken(authenticationService.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
            )
        ));
        return ResponseEntity.ok(jwtTokenDto);
    }

    @PostMapping("/register")
    ResponseEntity<JwtTokenDto> register(@RequestBody UserRequestDto request){
        JwtTokenDto jwtTokenDto = authenticationService.register(request);
        return ResponseEntity.ok(jwtTokenDto);
    }

    @PostMapping("/refresh")
    ResponseEntity<JwtTokenDto> refresh(@RequestBody JwtTokenDto jwtTokenDto){
        JwtTokenDto newTokenDto = new JwtTokenDto();
        newTokenDto.setToken(authenticationService.refreshToken(jwtTokenDto.getToken()));
        return ResponseEntity.ok(newTokenDto);
    }



    
}