package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotNull(message = "Selecione uma data inicial.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInicial;

    @NotNull(message = "Selecione uma data final.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataFinal;

    @JsonBackReference
    @OneToMany(mappedBy = "programaStart", cascade = CascadeType.REMOVE)
    private List<Grupo> grupos;
}
