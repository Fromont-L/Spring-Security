package fr.diginamic.springsecurity.controleur;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-app")
public class PageController {

    private final UserAppRepository repository;
    private final PasswordEncoder passwordEncoder;

    public PageController(UserAppRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
        return "redirect:/login";
    }
}
