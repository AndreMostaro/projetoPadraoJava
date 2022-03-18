package com.projeto.padrao.model;

import com.projeto.padrao.model.comum.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
public class Model extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", length = 250, nullable = false)
    private String nome;

    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    @Column(name = "email", length = 100 , nullable = false)
    private String email;

    // ENUM ativoInativo
    @Column(name = "ativo_inativo", length = 1)
    private String ativoInativo;

}
