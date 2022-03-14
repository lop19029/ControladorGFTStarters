package com.gft.ControleStarterMVC.repositories;

import com.gft.ControleStarterMVC.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectoRepository extends JpaRepository<Projeto, Long> {
}
