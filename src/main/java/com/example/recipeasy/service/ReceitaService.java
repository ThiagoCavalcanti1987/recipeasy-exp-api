package com.example.recipeasy.service;

import com.example.recipeasy.model.ReceitaModel;
import com.example.recipeasy.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceitaService {

    @Autowired
    ReceitaRepository receitaRepository;

    public ReceitaModel save(ReceitaModel receita) {
        return receitaRepository.save(receita);
    }

    public void deleteById(UUID id) {
        receitaRepository.deleteById(id);
    }
}
