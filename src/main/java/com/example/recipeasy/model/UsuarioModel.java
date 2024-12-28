package com.example.recipeasy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private UUID id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String senha;

        @Enumerated(EnumType.STRING)
        @Column(name = "role_type",nullable = false)
        private RoleType role;


        @Column(name = "data_criacao",nullable = false, updatable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime dataCriacao;


        @OneToMany(mappedBy = "usuario")
        private Set<ReceitaModel> receitas = new HashSet<>();

        @PrePersist
        protected void onCreate() {
                if (this.dataCriacao == null) {
                        this.dataCriacao = LocalDateTime.now();
                }
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
               if (this.role == RoleType.ADMIN){
                       return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                               new SimpleGrantedAuthority("ROLE_USER"));

               }else{
                       return List.of(new SimpleGrantedAuthority("ROLE_USER"));
               }
        }

        @Override
        public String getPassword() {
                return senha;
        }

        @Override
        public String getUsername() {
                return email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;//UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;//UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;//UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
                return true;//UserDetails.super.isEnabled();
        }
}
