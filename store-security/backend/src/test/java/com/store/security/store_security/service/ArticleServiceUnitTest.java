package com.store.security.store_security.service;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ArticleServiceUnitTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articoleRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ArticleMapper articleMapper;

    @BeforeEach
    public void init()
    {

        MockitoAnnotations.openMocks(this);
        articleService = new ArticleService(articoleRepository, stockRepository, articleMapper);
    }

    @Test
    public void savedArticle()
    {
        //given
        ArticleDto articleDto = ArticleDto.builder().name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
        ArticleEntity articleEntity = ArticleEntity.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        Mockito.when(articleMapper.toEntity(articleDto)).thenReturn(articleEntity);
        Mockito.when(articoleRepository.save(Mockito.any(ArticleEntity.class))).thenReturn(articleEntity);
        StockEntity stockEntity = StockEntity.builder().id(1).article(articleEntity).quantity(1).build();
        Mockito.when(stockRepository.save(Mockito.any(StockEntity.class))).thenReturn(stockEntity);
        //when
        boolean result = articleService.saveArticle(articleDto);
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deleteArticole()
    {
        //given
        ArticleEntity articleEntity = ArticleEntity.builder().id(1).name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        Mockito.when(articoleRepository.existsById(1)).thenReturn(true);
        //when
        boolean result = articleService.deleteArticle(1);
        //then
        Assertions.assertThat(result).isTrue();
    }
}
