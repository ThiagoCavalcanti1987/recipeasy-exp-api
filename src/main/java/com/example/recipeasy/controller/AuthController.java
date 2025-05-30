package com.example.recipeasy.controller;

import com.example.recipeasy.model.AuthModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthModel data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var authentication = this.authenticationManager.authenticate(userNamePassword);

        return ResponseEntity.ok().build();
    }
}
