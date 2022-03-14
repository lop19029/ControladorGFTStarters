package com.gft.ControleStarterMVC.security;

import com.gft.ControleStarterMVC.entities.User;
import com.gft.ControleStarterMVC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeDeUsuario) throws UsernameNotFoundException {

        User user = userRepository.findByNomeDeUsuario(nomeDeUsuario);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario não encontrado.");
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
