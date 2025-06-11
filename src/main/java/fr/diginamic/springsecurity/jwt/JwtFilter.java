package fr.diginamic.springsecurity.jwt;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "TOKEN_COOKIE";
    private static final String SECRET = "maSuperCleSecrete123maSuperCleSecrete123";

    private final UserAppRepository userRepository;

    public JwtFilter(UserAppRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> tokenCookie = Stream.of(cookies)
                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                    .findFirst();

            if (tokenCookie.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                String token = tokenCookie.get().getValue();

                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(SECRET.getBytes())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String userEmail = claims.getSubject();

                    // Vérification que l'utilisateur existe toujours en base
                    Optional<UserApp> userOpt = userRepository.findByEmail(userEmail);
                    if (userOpt.isPresent()) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userEmail,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                } catch (JwtException e) {
                    System.out.println("JWT invalide ou expiré : " + e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}