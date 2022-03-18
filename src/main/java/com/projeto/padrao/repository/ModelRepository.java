package com.projeto.padrao.repository;

import com.projeto.padrao.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    Optional<Model> findByCpf(String cpf);

}
