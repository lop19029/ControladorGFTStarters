package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Selecione uma data para a daily.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    private String fazendo;

    private String feito;

    private String impedimentos;

    @NotEmpty(message = "Presença não pode ser vazio")
    private String presenca;

    @NotNull(message = "Starter não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private Starter starter;

    @NotNull(message = "Modulo não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private Modulo modulo;


}
