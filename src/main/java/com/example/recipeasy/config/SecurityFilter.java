package com.example.recipeasy.config;

import com.example.recipeasy.repository.UsuarioRepository;
import com.example.recipeasy.service.DevTokenService;
import com.example.recipeasy.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final Optional<DevTokenService> devTokenService;

    public SecurityFilter(TokenService tokenService,
                          UsuarioRepository usuarioRepository,
                          Optional<DevTokenService> devTokenService) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.devTokenService = devTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (token != null) {
            boolean isDevAuthenticated = devTokenService
                    .map(service -> service.isDevToken(token))
                    .orElse(false);

            if (isDevAuthenticated) {
                var authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER"));
                var authentication = new UsernamePasswordAuthenticationToken(
                        "dev-admin", null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                String email = tokenService.validateToken(token);
                if (!email.isEmpty()) {
                    UserDetails user = usuarioRepository.findByEmail(email);
                    if (user != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
