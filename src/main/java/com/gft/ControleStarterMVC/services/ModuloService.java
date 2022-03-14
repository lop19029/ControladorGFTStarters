package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Modulo;
import com.gft.ControleStarterMVC.entities.ProgramaStart;
import com.gft.ControleStarterMVC.repositories.ModuloRepository;
import com.gft.ControleStarterMVC.repositories.ProgramaStartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public Modulo salvarModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    public List<Modulo> listarTodosOsModulos(){
        return moduloRepository.findAll();
    }

    public Modulo obterModulo(Long id) throws Exception {
        Optional<Modulo> modulo = moduloRepository.findById(id);

        if(modulo.isEmpty()) {
            throw new Exception("Modulo não encontrado.");
        } else {
            return modulo.get();
        }
    }

    public void deletarModulo(Modulo modulo) {
        moduloRepository.delete(modulo);
    }
}
