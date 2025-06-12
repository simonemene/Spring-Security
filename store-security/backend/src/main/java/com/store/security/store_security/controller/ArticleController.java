package com.store.security.store_security.controller;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticoleRepository;
import com.store.security.store_security.repository.StockRepository;
import com.store.security.store_security.service.IArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final IArticleService articleService;

    private final ArticoleRepository articoleRepository;

    private final StockRepository stockRepository;


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
    public ResponseEntity<String> decrementArticle(@RequestBody ArticleEntity article, @RequestParam("valueDecrement") int decrement)
    {
        StockEntity response = null;
        try{
            Optional<StockEntity> stockEntity = stockRepository.findByArticle(article);
            if(stockEntity.isPresent())
            {
                int quantity = stockEntity.get().getQuantity();
                if(decrement>quantity)
                {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Decrement failed");
                }
                stockEntity.get().setQuantity(quantity-decrement);
                response = stockRepository.save(stockEntity.get());
                if(response.getId()<0)
                {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Decrement failed");
                }
            }
        }catch(Exception e)
        {
             log.error(String.format("Decrement failed! decrement: %s, id article: %s",decrement,article.getName()));
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Decrement failed");
        }
            return ResponseEntity.status(HttpStatus.OK).body("Decrement success");
    }





}
