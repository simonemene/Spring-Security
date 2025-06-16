package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
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

	@Autowired
	private StockRepository stockRepository;

	//SUCCESS

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
				.tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
		//when
		boolean result = articleService.saveArticle(articleEntity);
		//then


		ArticleEntity resultArticle = null;
		Iterable<ArticleEntity> article = articleRepository.findAll();
		for (ArticleEntity entity : article) {
			resultArticle = entity;
			if (resultArticle.getName().equals("test")) {
				break;
			}
		}

		StockEntity resultStock = null;
		Iterable<StockEntity> stock = stockRepository.findAll();
		for (StockEntity entity : stock) {
			resultStock = entity;
			if (resultStock.getArticle().getName().equals(resultArticle.getName())) {
				break;
			}
		}

		Assertions.assertThat(result).isTrue();

		Assertions.assertThat(resultArticle.getName()).isEqualTo("test");
		Assertions.assertThat(resultArticle.getDescription()).isEqualTo("test");
		Assertions.assertThat(resultArticle.getPrice().stripTrailingZeros()).isEqualTo(new BigDecimal(1));
		Assertions.assertThat(resultArticle.getTmstInsert()).isEqualTo(LocalDateTime.of(2022, 1, 1, 1, 1));

		Assertions.assertThat(resultStock.getArticle()).usingRecursiveComparison().isEqualTo(resultArticle);
		Assertions.assertThat(resultStock.getQuantity()).isEqualTo(1);
	}

	@Test
	public void savedArticleQuantity()
	{
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
		ArticleEntity articleRequest = articleRepository.save(articleEntity);
		StockEntity stockEntity = StockEntity.builder().article(articleEntity).quantity(1).build();
		stockRepository.save(stockEntity);
		//when
		boolean result = articleService.saveArticleQuantity(articleRequest.getId(),4);
		//then


		ArticleEntity resultArticle = null;
		Iterable<ArticleEntity> article = articleRepository.findAll();
		for (ArticleEntity entity : article) {
			resultArticle = entity;
			if (resultArticle.getName().equals("test")) {
				break;
			}
		}

		StockEntity resultStock = null;
		Iterable<StockEntity> stock = stockRepository.findAll();
		for (StockEntity entity : stock) {
			resultStock = entity;
			if (resultStock.getArticle().getName().equals(resultArticle.getName())) {
				break;
			}
		}

		Assertions.assertThat(result).isTrue();

		Assertions.assertThat(resultArticle.getName()).isEqualTo("test");
		Assertions.assertThat(resultArticle.getDescription()).isEqualTo("test");
		Assertions.assertThat(resultArticle.getPrice().stripTrailingZeros()).isEqualTo(new BigDecimal(1));
		Assertions.assertThat(resultArticle.getTmstInsert()).isEqualTo(LocalDateTime.of(2022, 1, 1, 1, 1));

		Assertions.assertThat(resultStock.getArticle()).usingRecursiveComparison().isEqualTo(resultArticle);
		Assertions.assertThat(resultStock.getQuantity()).isEqualTo(5);
	}

	//FAILED

	@Test
	public void savedArticleQuantityFailed()
	{
		//given
		//when
		boolean result = articleService.saveArticleQuantity(2,4);
		//then
		Assertions.assertThat(result).isFalse();
	}

}
