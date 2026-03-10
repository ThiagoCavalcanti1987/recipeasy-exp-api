package com.example.recipeasy.dto;

import com.example.recipeasy.model.RoleType;
import com.example.recipeasy.model.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        RoleType role,
        String senha,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCriacao
) {

    public static UsuarioResponseDTO fromEntity(UsuarioModel usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getSenha(),
                usuario.getDataCriacao()
        );
    }
}
