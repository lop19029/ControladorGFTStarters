package com.gft.ControleStarterMVC.repositories;

import com.gft.ControleStarterMVC.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNomeDeUsuario(String nomeDeUsuario);

}
