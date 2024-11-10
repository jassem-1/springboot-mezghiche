package com.workshop.workshop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workshop.workshop.entity.Article;

public interface ArticleRepo extends MongoRepository<Article, String> {
    
}
