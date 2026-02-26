package com.example.recipeasy.controller;

import com.example.recipeasy.dto.LoginResponseDTO;
import com.example.recipeasy.model.AuthModel;
import com.example.recipeasy.model.UsuarioModel;
import com.example.recipeasy.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthModel data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var authentication = this.authenticationManager.authenticate(userNamePassword);

        String token = tokenService.generateToken((UsuarioModel) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
