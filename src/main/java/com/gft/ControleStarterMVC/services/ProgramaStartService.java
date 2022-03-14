package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.ProgramaStart;
import com.gft.ControleStarterMVC.entities.Tecnologia;
import com.gft.ControleStarterMVC.repositories.ProgramaStartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaStartService {

    @Autowired
    private ProgramaStartRepository programaStartRepository;

    public ProgramaStart salvarProgramaStart(ProgramaStart programaStart) {
        return programaStartRepository.save(programaStart);
    }

    public List<ProgramaStart> listarTodosOsProgramasStart(){
        return programaStartRepository.findAll();
    }

    public ProgramaStart obterProgramaStart(Long id) throws Exception {
        Optional<ProgramaStart> programaStart = programaStartRepository.findById(id);

        if(programaStart.isEmpty()) {
            throw new Exception("Programa não encontrado.");
        } else {
            return programaStart.get();
        }
    }

    public void deletarProgramaStart(Long id) {
        programaStartRepository.deleteById(id);
    }

}
