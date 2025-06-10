package fr.diginamic.springsecurity.controleur;

import fr.diginamic.springsecurity.jwt.JwtService;
import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jwt")
public class JwtAuthController {

    private final JwtService jwtService;
    private final UserAppRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthController(JwtService jwtService, UserAppRepository userRepo, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint pour générer un JWT si les credentials sont valides
    @PostMapping("/token")
    public String generateToken(@RequestBody UserApp loginRequest) {
        Optional<UserApp> userOpt = userRepo.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            return jwtService.generateToken(userOpt.get().getUsername());
        }
        return "Identifiants invalides";
    }

    // Endpoint pour valider un JWT
    @GetMapping("/verify/{token}")
    public boolean verifyToken(@PathVariable String token) {
        return jwtService.validateToken(token);
    }

    // Endpoint pour extraire le subject d’un JWT
    @GetMapping("/subject/{token}")
    public String getSubject(@PathVariable String token) {
        if (jwtService.validateToken(token)) {
            return jwtService.extractSubject(token);
        }
        return "Token invalide";
    }
}
