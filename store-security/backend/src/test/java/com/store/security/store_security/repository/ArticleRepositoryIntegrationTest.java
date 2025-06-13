package com.store.security.store_security.repository;

import com.store.security.store_security.entity.ArticleEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataJpaTest
public class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticleRepository articoleRepository;

    @Test
    public void saveArticle()
    {
        //given
        ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        //when
        ArticleEntity article = articoleRepository.save(articleEntity);
        //then
        Integer id = Integer.parseInt(String.valueOf(article.getId()));
        Assertions.assertThat(articoleRepository.findById(id).isPresent()).isTrue();
        Assertions.assertThat(articoleRepository.findById(id).get().getName()).isEqualTo("test");
        Assertions.assertThat(articoleRepository.findById(id).get().getDescription()).isEqualTo("test");
        Assertions.assertThat(articoleRepository.findById(id).get().getPrice()).isEqualTo(new BigDecimal(1));
        Assertions.assertThat(articoleRepository.findById(id).get().getTmstInsert()).isBefore(LocalDateTime.now());
    }

    @Test
    public void deleteArticle()
    {
        //given
        ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(2))
                .tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
        ArticleEntity article = articoleRepository.save(articleEntity);
        Integer id = Integer.parseInt(String.valueOf(article.getId()));
        //when
        articoleRepository.deleteById(id);
        //then
        Assertions.assertThat(articoleRepository.findById(id).isPresent()).isFalse();
    }


}
