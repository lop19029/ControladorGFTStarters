package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.repositories.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarterService {

    @Autowired
    private StarterRepository starterRepository;

    public Starter salvarStarter(Starter starter) {
        return starterRepository.save(starter);
    }

    public List<Starter> listarTodosOsStarters(){
        return starterRepository.findAll();
    }

    public List<Starter> listarStartersFiltrados(String nome, Long tecnologiaId) {
        List<Starter> starters;

        if(tecnologiaId == null){
            starters = starterRepository.findStarterByNome(nome);
        }
        else {
            starters = starterRepository.findStarterByNomeAndTecnologia(nome, tecnologiaId);
        }

        return starters;
    }

    public Starter obterStarter(Long id) throws Exception {
        Optional<Starter> starter = starterRepository.findById(id);

        if(starter.isEmpty()) {
            throw new Exception("Starter não encontrado.");
        } else {
            return starter.get();
        }
    }

    public void deletarStarter(Long id) {
        starterRepository.deleteById(id);
    }


}
