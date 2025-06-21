package com.store.security.store_security.controller;
/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.StockException;
import com.store.security.store_security.service.IArticleService;
import com.store.security.store_security.service.IStockService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ArticleControllerUnitTest {

    @Mock
    private IArticleService articleService;

    @Mock
    private IStockService stockService;


    @InjectMocks
    private ArticleController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        controller = new ArticleController(articleService, stockService);
    }


    //SUCCESS

    @Test
    @DisplayName("add article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void addArticle()
    {
        //given
        ArticleDto articleDto = ArticleDto.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
        Mockito.when(articleService.saveArticle(Mockito.any())).thenReturn(true);
        //when
        ResponseEntity<ArticleDto> response = controller.addArticle(articleDto);
        //then
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(articleDto);

    }

    @Test
    @DisplayName("delete article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void deleteArticle()
    {
        //given
        Mockito.when(articleService.deleteArticle(Mockito.any(Long.class))).thenReturn(true);
        //when
        ResponseEntity<String> response = controller.deleteArticole(1);
        //then
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isEqualTo("Articole deleted");
    }

    @Test
    @DisplayName("decrement article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void decrement()
    {
        //given
        Mockito.when(stockService.decrementArticle(Mockito.any(),Mockito.any(Integer.class))).thenReturn(true);
        ArticleEntity article = ArticleEntity.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        //when
        ResponseEntity<String> response = controller.decrementArticle(article,1);
        //then
        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isEqualTo("Decrement success");
    }

    //FAILED

    @Test
    @DisplayName("add article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void addArticleFailed()
    {
        //given
        ArticleDto articleDto = ArticleDto.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
        Mockito.when(articleService.saveArticle(Mockito.any())).thenReturn(false);
        //when
        //then
        Assertions.assertThatThrownBy(()->controller.addArticle(articleDto))
                .isInstanceOf(ArticleException.class)
                .hasMessageContaining("Article no added");

    }

    @Test
    @DisplayName("delete article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void deleteArticleFailed()
    {
        //given
        Mockito.when(articleService.deleteArticle(Mockito.any(Long.class))).thenReturn(false);
        //when
        //then
        Assertions.assertThatThrownBy(()->controller.deleteArticole(1))
                .isInstanceOf(ArticleException.class)
                .hasMessageContaining("Article no deleted");
    }

    @Test
    @DisplayName("decrement article")
    @WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
    public void decrementFailed()
    {
        //given
        Mockito.when(stockService.decrementArticle(Mockito.any(),Mockito.any(Integer.class))).thenReturn(false);
        ArticleEntity article = ArticleEntity.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        //when
        //then
        Assertions.assertThatThrownBy(()->controller.decrementArticle(article,1))
                .isInstanceOf(StockException.class)
                .hasMessageContaining("Decrement failed");
    }

}
*/