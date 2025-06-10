package fr.diginamic.springsecurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JWT {
    public static void main(String[] args) {
        // Clés proposées dans le TP
        String cle1 = "essayedoncCetteChouetteCleSecreteOnVerraSiCaMarche";
        String cle2 = "maToutAutantChouetteCleSecreteQueJeChoisiCommeJeVeux";

        // On commence avec cette clé pour voir si elle valide
        String secretKey = "maSuperCleSecrete123maSuperCleSecrete123";

        String[] tokens = {
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGVtcGxlIiwibWVzc2FnZSI6IlZvaWNpIHVuZSBjaGHDrm5lIMOgIHNpZ25lciIsImlhdCI6MTc0NDgzMTMxNX0.VIBNB1C1j93PUDrbmFJwbJXXbTYNPwEGJbkEQHVZoYg",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGVtcGxlIiwibWVzc2FnZSI6IlZvaWNpIHVuZSBjaGHDrm5lIMOgIHNpZ25lciIsImlhdCI6MTc0NDgzMTYwNn0.UZ6IO0Wvrnd4NP63diYjyvkNFNWI1NfDGP9lpfJyJSE",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGVtcGxlIiwibWVzc2FnZSI6IlZvaWNpIHVuZSBjaGHDrm5lIMOgIHRpZ25lciIsImlhdCI6MTc0NDgzMTY0NX0.5vpcu1T7DmXDuoCBLhBJAQGE3HpNUO41-Tr0rkGrDY0",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGVtcGxlIiwibWVzc2FnZSI6IlZvaWNpIHVuZSBjaGHDrm5lIMOgIHNpZ25lciIsImlhdCI6MTc0NDgzMTY3OH0.NXvOGwyMKQ9tK2z5dR6ER5tbf2plLlkxgJnCQ0lI13g"
        };

        System.out.println("Test avec la clé : " + secretKey);
        verifierTokens(tokens, secretKey);

        System.out.println("\nTest avec la clé : " + cle1);
        verifierTokens(tokens, cle1);

        System.out.println("\nTest avec la clé : " + cle2);
        verifierTokens(tokens, cle2);

        // Pour le dernier JWT donné dans l'énoncé (celui à analyser)
        String lastJwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKZSBuZSBzYWlzIHBhcyIsIm1lc3NhZ2UiOiLDoCBtb2kgc8OqbWUiLCJtZXNzYWdlLWNhY2jDqSI6InRyYXZhaWxsZSwgw6dhIGZpbml0IHRvdWpvdXJzIHBhciBwYXllciIsImxodW1vdXIiOiJjJ2VzdCBpbXBvcnRhbnQiLCJpYXQiOjE3NDQ4MzE5MTV9.wdaFguIzdkNKgVaYmSg5jHgYCDenufwjlJEL7T42fLA";

        System.out.println("\nAnalyse du JWT final :");
        analyserJwt(lastJwt, cle1);
        analyserJwt(lastJwt, cle2);
    }

    private static void verifierTokens(String[] tokens, String secretKey) {
        for (String jwt : tokens) {
            System.out.println("Vérification du JWT : " + jwt);
            try {
                Jws<Claims> claims = Jwts.parser()
                        .setSigningKey(secretKey.getBytes())
                        .parseClaimsJws(jwt);

                System.out.println("JWT valide !");
                System.out.println("Sujet : " + claims.getBody().getSubject());
                System.out.println("Message : " + claims.getBody().get("message"));
            } catch (SignatureException e) {
                System.out.println("JWT invalide : signature incorrecte");
            } catch (Exception e) {
                System.out.println("JWT invalide : " + e.getMessage());
            }
            System.out.println("-----------------------------------");
        }
    }

    private static void analyserJwt(String jwt, String secretKey) {
        System.out.println("Test avec la clé : " + secretKey);
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt);

            System.out.println("JWT valide !");
            System.out.println("Sujet : " + claims.getBody().getSubject());
            System.out.println("Messages présents :");
            claims.getBody().forEach((k,v) -> System.out.println("  " + k + " : " + v));
        } catch (Exception e) {
            System.out.println("JWT invalide avec cette clé.");
        }
    }
}
