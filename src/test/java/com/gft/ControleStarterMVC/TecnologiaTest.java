package com.gft.ControleStarterMVC;

import com.gft.ControleStarterMVC.repositories.TecnologiaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TecnologiaTest {

    @Autowired
    TecnologiaRepository tecnologiaRepository;

    @Test
    public void teste(){
        System.out.println("Comecei o teste");
        long count = tecnologiaRepository.count();
        System.out.println("Elements: " + count);
        tecnologiaRepository.findAll().forEach(System.out::println);
        System.out.println("Acabei o teste");
    }
}
