package com.pedromolon.reimburseflow.infrastructure.web.dto;

public record TokenResponseDTO(
        String token,
        String type
) {

    public TokenResponseDTO(String token) {
        this(token, "Bearer");
    }

}
