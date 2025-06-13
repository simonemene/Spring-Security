package com.store.security.store_security.controller;

import com.store.security.store_security.entity.ArticleEntity;
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
    public ResponseEntity<String> addOrder(@RequestBody ArticleEntity articleEntity) {
        return articleService.saveArticle(articleEntity) ?
         ResponseEntity.ok("Article added"):
         ResponseEntity.badRequest().body("Article not added");
    }

    @DeleteMapping("/deleteArticole/{id}")
    public ResponseEntity<String> deleteArticole(@PathVariable("id") long id) {
        return articleService.deleteArticle(id) ?
         ResponseEntity.ok("Articole deleted"):
         ResponseEntity.badRequest().body("Articole not deleted");
    }

    @PostMapping("/decrementArticle")
    public ResponseEntity<String> decrementArticle(@RequestBody ArticleEntity article, @RequestParam("valueDecrement") int decrement) {
        return stockService.decrementArticle(article, decrement) ?
                ResponseEntity.status(HttpStatus.OK).body("Decrement success") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Decrement failed");

    }
}
