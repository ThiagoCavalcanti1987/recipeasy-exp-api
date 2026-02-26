package com.example.recipeasy.service;

import com.example.recipeasy.dto.UsuarioRequestDTO;
import com.example.recipeasy.dto.UsuarioResponseDTO;
import com.example.recipeasy.exception.EmailAlreadyExistsException;
import com.example.recipeasy.exception.UsuarioNotFoundException;
import com.example.recipeasy.model.UsuarioModel;
import com.example.recipeasy.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponseDTO::fromEntity)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com id " + id + " não encontrado"));
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {
        if (usuarioRepository.findByEmail(request.email()) != null) {
            throw new EmailAlreadyExistsException("Email " + request.email() + " já está cadastrado");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole(request.role());

        UsuarioModel saved = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntity(saved);
    }

    @Transactional
    public UsuarioResponseDTO update(UUID id, UsuarioRequestDTO request) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com id " + id + " não encontrado"));

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole(request.role());

        UsuarioModel updated = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuário com id " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
