package com.example.recipeasy.repository;
import com.example.recipeasy.model.RefeicaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RefeicaoRepository extends JpaRepository<RefeicaoModel, UUID>{
}
