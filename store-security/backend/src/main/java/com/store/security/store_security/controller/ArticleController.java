package com.store.security.store_security.controller;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ListArticleDto;
import com.store.security.store_security.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final IArticleService articleService;

    @Operation(
            summary = "Get all articles",
            description = "REST API to get all articles"
    )
    @ApiResponse(
            responseCode = "200",
            description  = "HTTP Status 200 : OK"
    )
    @GetMapping
    public ResponseEntity<ListArticleDto> getAllArticles()
    {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.allArticle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable("id") Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticle(id));
    }
}
