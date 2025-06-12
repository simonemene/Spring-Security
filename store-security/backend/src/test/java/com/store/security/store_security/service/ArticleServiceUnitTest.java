package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticoleRepository;
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
    private ArticoleRepository articoleRepository;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void savedArticle()
    {
        //given
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(1);
        articleEntity.setName("test");
        articleEntity.setDescription("test");
        articleEntity.setPrice(new BigDecimal(1));
        articleEntity.setTmstInsert(LocalDateTime.now());
        Mockito.when(articoleRepository.save(articleEntity)).thenReturn(articleEntity);
        //when
        boolean result = articleService.saveArticle(articleEntity);
        //then
        Assertions.assertThat(result).isTrue();

    }
}
