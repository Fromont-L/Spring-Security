package fr.diginamic.springsecurity.service;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserAppRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAppRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserApp> authenticate(String email, String password) {
        Optional<UserApp> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            UserApp user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public UserApp createUser(String username, String email, String rawPassword) {
        UserApp user = new UserApp();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }
}