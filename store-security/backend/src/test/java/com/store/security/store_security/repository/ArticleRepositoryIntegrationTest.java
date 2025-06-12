package com.store.security.store_security.repository;

import com.store.security.store_security.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataJpaTest
public class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticoleRepository articoleRepository;


    @Test
    public void saveArticle()
    {
        //given
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName("test");
        articleEntity.setDescription("test");
        articleEntity.setPrice(new BigDecimal(1));
        articleEntity.setTmstInsert(LocalDateTime.now());
        //when
        articoleRepository.save(articleEntity);
        //then
        Assertions.assertThat(articoleRepository.findById(1).isPresent()).isTrue();
        Assertions.assertThat(articoleRepository.findById(1).get().getName()).isEqualTo("test");
        Assertions.assertThat(articoleRepository.findById(1).get().getDescription()).isEqualTo("test");
        Assertions.assertThat(articoleRepository.findById(1).get().getPrice()).isEqualTo(new BigDecimal(1));
        Assertions.assertThat(articoleRepository.findById(1).get().getTmstInsert()).isBefore(LocalDateTime.now());
    }


}
