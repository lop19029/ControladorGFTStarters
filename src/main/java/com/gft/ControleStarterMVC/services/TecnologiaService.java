package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Tecnologia;
import com.gft.ControleStarterMVC.repositories.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnologiaService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    public Tecnologia salvarTecnologia(Tecnologia tecnologia) {
        return tecnologiaRepository.save(tecnologia);
    }

    public List<Tecnologia> listarTodasAsTecnologias(){
        return tecnologiaRepository.findAll();
    }

    public Tecnologia obterTecnologia(Long id) throws Exception {
        Optional<Tecnologia> tecnologia = tecnologiaRepository.findById(id);

        if(tecnologia.isEmpty()) {
            throw new Exception("Tecnologia não encontrada.");
        } else {
            return tecnologia.get();
        }
    }

    public void deletarTecnologia(Long id) {
        tecnologiaRepository.deleteById(id);
    }

}
