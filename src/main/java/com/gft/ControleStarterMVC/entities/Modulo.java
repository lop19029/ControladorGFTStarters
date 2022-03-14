package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String nome;

    @JsonBackReference
    @OneToMany(mappedBy = "modulo", cascade = CascadeType.REMOVE)
    private List<Daily> dailies;

    @JsonBackReference
    @OneToMany(mappedBy = "modulo", cascade = CascadeType.REMOVE)
    private List<Projeto> projetos;

//    @ManyToMany(mappedBy = "modulos", cascade = CascadeType.REMOVE)
//    private List<Starter> starters;
}
