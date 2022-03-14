package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tecnologia não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private Tecnologia tecnologia;

    @NotNull(message = "Scrum Master não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private User scrumMaster;

    @NotNull(message = "Programa Start não pode ser nulo.")
    @JsonManagedReference
    @ManyToOne
    private ProgramaStart programaStart;

    @JsonBackReference
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.REMOVE)
    private List<Starter> starters;

}
