package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.Modulo;
import com.gft.ControleStarterMVC.repositories.GrupoRepository;
import com.gft.ControleStarterMVC.repositories.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo salvarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Grupo> listarTodosOsGrupos(){
        return grupoRepository.findAll();
    }

    public Grupo obterGrupo(Long id) throws Exception {
        Optional<Grupo> grupo = grupoRepository.findById(id);

        if(grupo.isEmpty()) {
            throw new Exception("Grupo não encontrado.");
        } else {
            return grupo.get();
        }
    }

    public void deletarGrupo(Long id) {
        grupoRepository.deleteById(id);
    }
}
