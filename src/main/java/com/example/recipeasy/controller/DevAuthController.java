package com.example.recipeasy.controller;

import com.example.recipeasy.dto.LoginResponseDTO;
import com.example.recipeasy.service.DevTokenService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exclusivo para desenvolvimento.
 * Só é carregado quando o profile "dev" está ativo.
 * NÃO existe em produção.
 */
@RestController
@RequestMapping("/auth")
@Profile("dev")
public class DevAuthController {

    private final DevTokenService devTokenService;

    public DevAuthController(DevTokenService devTokenService) {
        this.devTokenService = devTokenService;
    }

    @GetMapping("/dev-token")
    public ResponseEntity<LoginResponseDTO> devToken() {
        String token = devTokenService.generateDevToken();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
