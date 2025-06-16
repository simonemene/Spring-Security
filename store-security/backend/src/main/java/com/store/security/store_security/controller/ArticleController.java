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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final IArticleService articleService;

    private final IStockService stockService;


    @PostMapping("/addArticle")
    public ResponseEntity<String> addArticle(@RequestBody ArticleDto articleDto) {
        if(articleService.saveArticle(articleDto)) {
            return ResponseEntity.status(HttpStatus.OK).body("Article added");
        }
         throw new ArticleException("Article no added");
    }

    @PostMapping("/addArticle/{id}/{quantity}")
    public ResponseEntity<String> addArticleQuantity(@PathVariable("id") long id, @PathVariable("quantity") int quantity) {
        if(articleService.saveArticleQuantity(id, quantity)) {

            return ResponseEntity.status(HttpStatus.OK).body("Article quantity added");
        }
        throw new ArticleException("Article quantity not added");
    }

    @DeleteMapping("/deleteArticle/{id}")
    public ResponseEntity<String> deleteArticole(@PathVariable("id") long id) {
        if(articleService.deleteArticle(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Articole deleted");
        }
        throw new ArticleException("Article no deleted");
    }

    @PostMapping("/decrementArticle")
    public ResponseEntity<String> decrementArticle(@RequestBody ArticleEntity article, @RequestParam("valueDecrement") int decrement) {
        if(stockService.decrementArticle(article, decrement))
        {
            return  ResponseEntity.status(HttpStatus.OK).body("Decrement success");
        }
        throw new StockException("Decrement failed");

    }
}
