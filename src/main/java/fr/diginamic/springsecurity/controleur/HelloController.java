package fr.diginamic.springsecurity.controleur;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "Hello " + username + " ! Vous êtes authentifié.";
    }

    @GetMapping("/hello/public")
    public String helloPublic() {
        return "Hello public ! Cette page est accessible sans authentification.";
    }
}