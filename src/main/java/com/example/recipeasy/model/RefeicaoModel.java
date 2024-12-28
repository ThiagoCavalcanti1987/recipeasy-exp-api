package com.example.recipeasy.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "refeicoes")
public class RefeicaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column
    private String tipo;
    @Column
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToMany
    @JoinTable(
            name = "refeicoes_receitas",
            joinColumns = @JoinColumn(name = "refeicao_id"),
            inverseJoinColumns = @JoinColumn(name = "receita_id")
    )
    private Set<ReceitaModel> receitas = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Set<ReceitaModel> getReceitas() {
        return receitas;
    }

    public void setReceitas(Set<ReceitaModel> receitas) {
        this.receitas = receitas;
    }
}
