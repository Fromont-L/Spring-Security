package fr.diginamic.springsecurity.controleur;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControleurLogin {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/moi")
    public String quiSuisJe(Authentication authentication) {
        return "Connecté en temps que : " + authentication.getName();
    }
}
