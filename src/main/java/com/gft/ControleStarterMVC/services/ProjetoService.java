package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Grupo;
import com.gft.ControleStarterMVC.entities.Projeto;
import com.gft.ControleStarterMVC.repositories.GrupoRepository;
import com.gft.ControleStarterMVC.repositories.ProjectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjectoRepository projectoRepository;

    public Projeto salvarProjeto(Projeto projeto) {
        return projectoRepository.save(projeto);
    }

    public List<Projeto> listarTodosOsProjetos(){
        return projectoRepository.findAll();
    }

    public Projeto obterProjeto(Long id) throws Exception {
        Optional<Projeto> projeto = projectoRepository.findById(id);

        if(projeto.isEmpty()) {
            throw new Exception("Projeto não encontrado.");
        } else {
            return projeto.get();
        }
    }

    public void deletarProjeto(Long id) {
        projectoRepository.deleteById(id);
    }
}
