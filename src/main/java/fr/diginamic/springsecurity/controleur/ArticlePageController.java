package fr.diginamic.springsecurity.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlePageController {

    @GetMapping("/article-list")
    public String showArticleList() {
        return "article-list";
    }

    @GetMapping("/article-form")
    public String showArticleForm() {
        return "article-form";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // ça correspond à register.html
    }
}

