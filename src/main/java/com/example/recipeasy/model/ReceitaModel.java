package com.example.recipeasy.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "receitas")
public class ReceitaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(length = 1000)
    private String instrucoes;

    @Column(name = "tempo_preparo")
    private Duration tempoPreparo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToMany(mappedBy = "receitas")
    private Set<RefeicaoModel> refeicoes = new HashSet<>();

    // Construtor padrão
    public ReceitaModel() {
    }

    // Construtor completo
    public ReceitaModel(UUID id, String nome, String descricao, String instrucoes, Duration tempoPreparo, UsuarioModel usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.instrucoes = instrucoes;
        this.tempoPreparo = tempoPreparo;
        this.usuario = usuario;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public Duration getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Duration tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Set<RefeicaoModel> getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(Set<RefeicaoModel> refeicoes) {
        this.refeicoes = refeicoes;
    }

}
