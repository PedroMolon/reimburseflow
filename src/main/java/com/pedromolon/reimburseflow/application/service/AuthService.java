package com.pedromolon.reimburseflow.application.service;

import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringUserRepository;
import com.pedromolon.reimburseflow.infrastructure.security.JwtTokenProvider;
import com.pedromolon.reimburseflow.infrastructure.web.dto.LoginRequestDTO;
import com.pedromolon.reimburseflow.infrastructure.web.dto.TokenResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final SpringUserRepository springUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider jwtTokenProvider, SpringUserRepository springUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.springUserRepository = springUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDTO authenticate(LoginRequestDTO request) {
        UserEntity userEntity = springUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User or password invalid"));

        if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User or password invalid");
        }

        String token = jwtTokenProvider.generateToken(userEntity);

        return new TokenResponseDTO(token);
    }

}
