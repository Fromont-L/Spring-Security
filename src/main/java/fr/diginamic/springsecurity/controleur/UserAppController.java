package fr.diginamic.springsecurity.controleur;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAppController {

    private final UserAppRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserAppController(UserAppRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/users")
    public List<UserApp> getAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam("pass") String password) {
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(email);

        repository.save(user);
        return "redirect:/login"; // Redirige vers la page de connexion après inscription
    }

}
