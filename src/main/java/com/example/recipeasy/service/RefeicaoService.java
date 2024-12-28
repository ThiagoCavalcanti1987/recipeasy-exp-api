package com.example.recipeasy.service;

import com.example.recipeasy.model.RefeicaoModel;
import com.example.recipeasy.repository.RefeicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefeicaoService {
    @Autowired
    RefeicaoRepository refeicaoRepository;

    public RefeicaoModel save(RefeicaoModel refeicao) {
        return refeicaoRepository.save(refeicao);
    }

    public void deleteById(UUID id) {
        refeicaoRepository.deleteById(id);
    }
}
