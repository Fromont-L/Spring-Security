package fr.diginamic.springsecurity.controleur;

import fr.diginamic.springsecurity.dto.LoginRequest;
import fr.diginamic.springsecurity.jwt.JwtService;
import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Optional<UserApp> userOpt = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if (userOpt.isPresent()) {
            UserApp user = userOpt.get();
            String token = jwtService.generateToken(user.getEmail());

            // Création du cookie avec le JWT
            Cookie jwtCookie = new Cookie("TOKEN_COOKIE", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false); // true en production avec HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600); // 1 heure

            response.addCookie(jwtCookie);

            return ResponseEntity.ok("Connexion réussie pour " + user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Suppression du cookie en définissant sa durée à 0
        Cookie jwtCookie = new Cookie("TOKEN_COOKIE", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Déconnexion réussie");
    }
}