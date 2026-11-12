package com.pedromolon.reimburseflow.infrastructure.security;

import com.pedromolon.reimburseflow.infrastructure.entity.RoleEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken(UserEntity user){
        Instant now = Instant.now();
        long expiresIn = 3600L;

        List<String> roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("minha-api")
                .subject(user.getName())
                .claim("userId", user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", roles)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
