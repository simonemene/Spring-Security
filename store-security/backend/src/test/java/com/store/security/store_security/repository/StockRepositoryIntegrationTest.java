package com.store.security.store_security.repository;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class StockRepositoryIntegrationTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private  ArticleRepository articleRepository;


	@Test
	public void findArticleById()
	{
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
		articleRepository.save(articleEntity);
		StockEntity stockEntity = new StockEntity();
		stockEntity.setArticle(articleEntity);
		stockEntity.setQuantity(2);
        stockRepository.save(stockEntity);
		//when
		Optional<StockEntity> stock = stockRepository.findByArticle(articleEntity);
		//then
		Assertions.assertThat(stock.isPresent()).isTrue();
		StockEntity result = stock.get();
		Assertions.assertThat(result.getQuantity()).isEqualTo(2);
		Assertions.assertThat(result.getArticle()).usingRecursiveComparison().isEqualTo(articleEntity);
	}
}
