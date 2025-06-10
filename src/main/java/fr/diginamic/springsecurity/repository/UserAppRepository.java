package fr.diginamic.springsecurity.repository;

import fr.diginamic.springsecurity.model.UserApp;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    @PostConstruct
    default void check() {
        System.out.println("UserAppRepository chargé");
    }

    Optional<UserApp> findByEmail(String email);

}

