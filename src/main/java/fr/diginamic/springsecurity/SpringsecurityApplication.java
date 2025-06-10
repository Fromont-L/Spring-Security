package fr.diginamic.springsecurity;

import fr.diginamic.springsecurity.model.UserApp;
import fr.diginamic.springsecurity.repository.UserAppRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

	// Ce bean s'exécute au démarrage de l'application
	@Bean
	public CommandLineRunner initDatabase(UserAppRepository repository) {
		return args -> {
			// Création d'un utilisateur UserApp
			UserApp user = new UserApp();
			user.setUsername("jaaj");
			user.setEmail("a@a.com");
			user.setPassword("$2a$12$QvqaNkXxLgfUUmr6wQsEr.u0kxdS8tTLmAkEVHIuYcl6qK62HCIui");

			// Sauvegarde dans la base H2
			repository.save(user);

			System.out.println(">>> Utilisateur ajouté en base : " + user);
		};
	}
}
