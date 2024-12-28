package com.example.recipeasy.repository;

import com.example.recipeasy.model.ReceitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaModel, UUID> {
}
