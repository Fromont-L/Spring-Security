package fr.diginamic.springsecurity.controleur;

import fr.diginamic.springsecurity.model.Article;
import fr.diginamic.springsecurity.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("articles", repository.findAll());
        return "article-list";
    }

    @GetMapping("/create")
    public String form() {
        return "article-form";
    }

    @PostMapping("/create")
    public String submit(@RequestParam String titre, @RequestParam String contenu) {
        Article article = new Article();
        article.setTitre(titre);
        article.setContenu(contenu);
        repository.save(article);
        return "redirect:/article/list";
    }
}
