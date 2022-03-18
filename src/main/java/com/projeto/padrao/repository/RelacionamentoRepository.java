package com.projeto.padrao.repository;

import com.projeto.padrao.model.Relacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelacionamentoRepository extends JpaRepository<Relacionamento, Integer> {
}
