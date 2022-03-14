package com.gft.ControleStarterMVC.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gft.ControleStarterMVC.enums.NivelDeAcesso;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email não pode ser vazio.")
    private String nomeDeUsuario; //Uses email as username for business purposes

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio.")
    private String sobrenome;

    @NotEmpty(message = "Quatro letras não podem estar em branco.")
    @Size(min = 4, max = 4, message = "Dever ser quatro letras.")
    private String quatroLetras;

    @NotEmpty(message = "Password não pode ser vazio.")
    private String password;

    @NotNull(message = "Nivel de acesso não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelDeAcesso nivelDeAcesso;

    @JsonBackReference
    @OneToMany(mappedBy = "scrumMaster", cascade = CascadeType.REMOVE)
    private List<Grupo> grupos;
}
