package com.gft.ControleStarterMVC;

import com.gft.ControleStarterMVC.entities.User;
import com.gft.ControleStarterMVC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Populating Database...");

        Optional<User> clecio = this.userRepository.findById(1L);
        if(clecio.isEmpty()){
            throw new Exception("User with id 1 not found");
        } else {
            clecio.get().setPassword(passwordEncoder.encode("Gft2021"));
        }

        Optional<User> user = this.userRepository.findById(2L);
        if(user.isEmpty()){
            throw new Exception("User with id 2 not found");
        } else {
            user.get().setPassword(passwordEncoder.encode("password"));
        }

        Optional<User> user2 = this.userRepository.findById(3L);
        if(user2.isEmpty()){
            throw new Exception("User with id 3 not found");
        } else {
            user2.get().setPassword(passwordEncoder.encode("password"));
        }

        Optional<User> admin = this.userRepository.findById(4L);
        if(admin.isEmpty()){
            throw new Exception("User with id 4 not found");
        } else {
            admin.get().setPassword(passwordEncoder.encode("password"));
        }

        this.userRepository.save(clecio.get());
        this.userRepository.save(user.get());
        this.userRepository.save(user2.get());
        this.userRepository.save(admin.get());

    }
}
