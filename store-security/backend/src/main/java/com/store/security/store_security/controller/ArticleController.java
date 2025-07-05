package com.store.security.store_security.controller;

import com.store.security.store_security.dto.ListArticleDto;
import com.store.security.store_security.service.IArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping
    public ResponseEntity<ListArticleDto> getAllArticles()
    {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.allArticle());
    }
}
