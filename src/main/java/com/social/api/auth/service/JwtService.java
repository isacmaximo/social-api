package com.social.api.auth.service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.social.api.auth.repository.UserRepository;

@Service
public class JwtService {
    final JwtEncoder jwtEncoder;
    final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expires = 3600L;

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
                
        UUID uid = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"))
                .getId();

        var claims = JwtClaimsSet.builder()
                .issuer("social-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expires))
                .subject(authentication.getName())
                .claim("uid", uid.toString())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(String token) {
        Instant now = Instant.now();
        long expires = 3600L;
        String scopes = jwtDecoder.decode(token).getClaim("scope").toString();
        
        var claimsRefresh = JwtClaimsSet.builder()
                .issuer("social-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expires))
                .subject(jwtDecoder.decode(token).getSubject())
                .claim("scope", scopes)
                .claim("uid", jwtDecoder.decode(token).getClaim("uid").toString())
                .claim("type", "refresh")
                .build();
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsRefresh)).getTokenValue();
    
    }

    public Jwt decode(String token) {
        return jwtDecoder.decode(token);
    }

    public String generateTokenFrom(String subject, String scopes) {
        Instant now = Instant.now();
        long expires = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("social-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expires))
                .subject(subject)
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
