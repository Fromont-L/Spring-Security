package fr.diginamic.springsecurity.cookie;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class CookieController {

    @GetMapping("/get-cookie")
    public ResponseEntity<String> getCookie() {
        String cookieName = "monCookie";
        String cookieValue = "valeurDuCookie";

        ResponseCookie tokenCookie = ResponseCookie.from(cookieName, cookieValue)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofSeconds(120))
                .secure(true)
                .sameSite("Strict")
                .build();



        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                .body("cookie posé avec succès");
    }
}
