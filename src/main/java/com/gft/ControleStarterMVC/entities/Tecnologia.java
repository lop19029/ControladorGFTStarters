package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tecnologia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String nome;

    private String descricao;

    @JsonBackReference
    @OneToMany(mappedBy = "tecnologia", cascade = CascadeType.REMOVE)
    private List<Grupo> grupos;

}
