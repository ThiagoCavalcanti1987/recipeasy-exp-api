package com.example.recipeasy.controller;

import com.example.recipeasy.model.UsuarioModel;
import com.example.recipeasy.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/recipeasy/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/findAll")
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable UUID id) {
        Optional<UsuarioModel> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public UsuarioModel createUsuario(@RequestBody UsuarioModel usuario) {
        return usuarioService.save(usuario);
    }

    /*@PutMapping("/update/{id}")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable UUID id, @RequestBody UsuarioModel usuarioDetails) {
        Optional<UsuarioModel> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {

            return ResponseEntity.ok(usuarioService.save(existingUsuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable UUID id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
