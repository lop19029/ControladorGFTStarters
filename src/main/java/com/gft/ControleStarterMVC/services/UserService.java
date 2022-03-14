package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.User;
import com.gft.ControleStarterMVC.enums.NivelDeAcesso;
import com.gft.ControleStarterMVC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarTodosOsSrumMasters() {
        List<User> scrumMasters = new ArrayList<>();
        List<User> users = userRepository.findAll();

        for (User u:
                users) {
            //Only the scrumMasters have "Restricted access"
            if(u.getNivelDeAcesso().equals(NivelDeAcesso.RESTRICTED)){
                scrumMasters.add(u);
            }
        }
        return scrumMasters;
    }

    public List<Grupo> listarGruposDeScrumMaster(User scrum){
        List<Grupo> grupos = new ArrayList<>();
        for (Grupo g:
             scrum.getGrupos()) {
            grupos.add(g);
        }
        return grupos;
    }

    public User obterUsuarioPorNomeDeUsuario(String nome){
        return userRepository.findByNomeDeUsuario(nome);
    }
}
