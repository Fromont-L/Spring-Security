package fr.diginamic.springsecurity.service;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAppRepository repository;

    public CustomUserDetailsService(UserAppRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
