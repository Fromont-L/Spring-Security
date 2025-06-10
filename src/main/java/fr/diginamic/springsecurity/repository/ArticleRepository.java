package fr.diginamic.springsecurity.repository;

import fr.diginamic.springsecurity.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
