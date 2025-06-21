package com.store.security.store_security.controller;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.StockException;
import com.store.security.store_security.service.IArticleService;
import com.store.security.store_security.service.IStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final IArticleService articleService;

    private final IStockService stockService;


    @PostMapping
    public ResponseEntity<ArticleDto> addArticle(@RequestBody ArticleDto articleDto) {
        if(articleService.saveArticle(articleDto)) {
            return ResponseEntity.status(HttpStatus.OK).body(articleDto);
        }
         throw new ArticleException("Article no added");
    }

    @DeleteMapping("/deleteArticle/{id}")
    public ResponseEntity<String> deleteArticole(@PathVariable("id") long id) {
        if(articleService.deleteArticle(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Articole deleted");
        }
        throw new ArticleException("Article no deleted");
    }


}
*/