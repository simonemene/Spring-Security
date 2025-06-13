package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ArticleServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository articleRepository;

	@Test
	public void deleteArticle()
	{
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
		ArticleEntity savedArticle = articleRepository.save(articleEntity);
		//when
		articleService.deleteArticle(savedArticle.getId());
		//then
		Assertions.assertThat(articleRepository.findById(Integer.parseInt(String.valueOf(savedArticle.getId())))).isEmpty();
	}

	@Test
	public void savedArticle()
	{
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
		articleEntity.setTmstInsert(LocalDateTime.now());
		//when
		boolean result = articleService.saveArticle(articleEntity);
		//then
		Assertions.assertThat(result).isTrue();
	}
}
