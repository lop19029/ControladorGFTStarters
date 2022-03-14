package com.gft.ControleStarterMVC.repositories;

import com.gft.ControleStarterMVC.entities.Starter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StarterRepository extends JpaRepository<Starter, Long> {

    @Query("SELECT s FROM Starter s WHERE s.nome LIKE ?1%")
    List<Starter> findStarterByNome(String nome);

    @Query("SELECT s FROM Starter s WHERE s.nome LIKE ?1% AND s.grupo.tecnologia.id = ?2")
    List<Starter> findStarterByNomeAndTecnologia(String nome, Long tecnologiaId);
}
