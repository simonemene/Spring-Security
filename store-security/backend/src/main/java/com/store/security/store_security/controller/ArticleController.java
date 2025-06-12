package com.store.security.store_security.controller;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticoleRepository articoleRepository;


    @PostMapping("/addArticle")
    public ResponseEntity<String> addOrder(@RequestBody ArticleEntity articleEntity) {
        ArticleEntity article = articoleRepository.save(articleEntity);
        if(article.getId() > 0)
        {
            return ResponseEntity.ok("Article added");
        }
        return ResponseEntity.badRequest().body("Article not added");
    }





}
