package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gft.ControleStarterMVC.enums.Etapa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentarios;

    @NotEmpty(message = "Avaliação não pode ser vazio.")
    private String avaliacao;

    @NotNull(message = "Etapa Start não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etapa etapa;

    @NotNull(message = "Modulo Start não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private Modulo modulo;

    @NotNull(message = "Starter Start não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private Starter starter;

}
