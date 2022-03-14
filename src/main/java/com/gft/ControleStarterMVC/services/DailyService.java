package com.gft.ControleStarterMVC.services;

import com.gft.ControleStarterMVC.entities.Daily;
import com.gft.ControleStarterMVC.entities.Starter;
import com.gft.ControleStarterMVC.repositories.DailyRepository;
import com.gft.ControleStarterMVC.repositories.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyService {

    @Autowired
    private DailyRepository dailyRepository;

    public Daily salvarDaily(Daily daily) {
        return dailyRepository.save(daily);
    }

    public List<Daily> listarTodasAsDailies(){
        return dailyRepository.findAll();
    }

    public Daily obterDaily(Long id) throws Exception {
        Optional<Daily> daily = dailyRepository.findById(id);

        if(daily.isEmpty()) {
            throw new Exception("Daily não encontrada.");
        } else {
            return daily.get();
        }
    }

    public void deletarDaily(Long id) {
        dailyRepository.deleteById(id);
    }
}
