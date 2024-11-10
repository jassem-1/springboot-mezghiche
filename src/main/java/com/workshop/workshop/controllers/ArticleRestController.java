package com.workshop.workshop.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workshop.workshop.entity.Article;
import com.workshop.workshop.repositories.ArticleRepo;
import com.workshop.workshop.repositories.ProviderRepo;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
@RequestMapping("/articles")
public class ArticleRestController {

    private final ArticleRepo articleRepository;
    private final ProviderRepo providerRepository;

    @Autowired
    public ArticleRestController(ArticleRepo articleRepo,
                                 ProviderRepo providerRepository) {
        this.articleRepository = articleRepo; // Initialize articleRepository
        this.providerRepository = providerRepository;
    }

    // Get all articles
    @GetMapping("/list")
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // Create a new article
    @PostMapping("/add/{providerId}")
    public Article createArticle(@PathVariable(value = "providerId") String providerId,
                                 @Valid @RequestBody Article article) {
        return providerRepository.findById(providerId).map(provider -> {
            article.setProviderId(providerId);
            return articleRepository.save(article);
        }).orElseThrow(() -> new NoSuchElementException("ProviderId " + providerId + " not found"));
    }

    // Update an article
    @PutMapping("/update/{providerId}/{articleId}")
    public Article updateArticle(@PathVariable(value = "providerId") String providerId,
                                 @PathVariable(value = "articleId") String articleId,
                                 @Valid @RequestBody Article articleRequest) {
        if (!providerRepository.existsById(providerId)) {
            throw new NoSuchElementException("ProviderId " + providerId + " not found");
        }

        return articleRepository.findById(articleId).map(article -> {
            article.setPrice(articleRequest.getPrice());
            article.setLabel(articleRequest.getLabel());
            article.setPicture(articleRequest.getPicture());
            article.setProviderId(providerId); // Ensure providerId is updated
            return articleRepository.save(article);
        }).orElseThrow(() -> new NoSuchElementException("ArticleId " + articleId + " not found"));
    }

    // Delete an article
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable(value = "articleId") String articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NoSuchElementException("Article not found with id " + articleId));
    }
}
