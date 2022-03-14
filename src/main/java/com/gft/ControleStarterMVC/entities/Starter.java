package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class Starter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String foto;

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String nome;

    @NotEmpty(message = "Sobrenome não pode ser vazio.")
    private String sobrenome;

    @NotEmpty(message = "Quatro letras não pode ser vazio.")
    @Size(min = 4, max = 4, message = "Deve ser quatro letras.")
    private String quatroLetras;

    private Integer nota;

    @NotNull(message = "O Starter precisa ter um grupo.")
    @JsonManagedReference
    @ManyToOne
    private Grupo grupo;

    @JsonBackReference
    @OneToMany(mappedBy = "starter", cascade = CascadeType.REMOVE)
    private List<Daily> dailies;

    @JsonBackReference
    @OneToMany(mappedBy = "starter", cascade = CascadeType.REMOVE)
    private List<Projeto> projetos;

    @Transient
    public String getFotoPath() {
        if (foto == null || id == null) {
            return null;
        }
        else if (foto.equals("starter-default.png")){
            return "/starter-fotos/0/starter-default.png";
        }
        else { return "/starter-fotos/" + id + "/" + foto;}
    }

}
