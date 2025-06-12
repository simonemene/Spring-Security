package com.store.security.store_security.controller;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deleteArticole/{id}")
    public ResponseEntity<String> deleteArticole(@PathVariable("id") long id) {
        try {
            articoleRepository.deleteById(Integer.parseInt(String.valueOf(id)));
        }catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Articole not deleted");
        }
        return ResponseEntity.ok("Articole deleted");
    }





}
