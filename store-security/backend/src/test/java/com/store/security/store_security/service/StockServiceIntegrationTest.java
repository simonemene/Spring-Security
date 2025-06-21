package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class StockServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private StockService stockService;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private StockRepository stockRepository;


	@Test
	public void getAllStock()
	{
       //given
		StockEntity stockEntity = StockEntity.builder().article(List.of(articleEntity)).quantity(1).build();
		StockEntity stockEntity1 = StockEntity.builder().build();
		ArticleEntity articleEntity = ArticleEntity.builder().stock(stockEntity).name("test")
				.description("test").price(new BigDecimal(1)).build();
		ArticleEntity articleEntity1 = ArticleEntity.builder().stock(stockEntity1).name("test1")
				.description("test1").price(new BigDecimal(15)).build();



		articleRepository.save(articleEntity);
		articleRepository.save(articleEntity1);
		StockEntity stock = stockRepository.save(stockEntity);
		StockEntity stock1 = stockRepository.save(stockEntity1);

		ArticleEntity articleEntity2 = ArticleEntity.builder().name("test2").description("test2").price(new BigDecimal(6)).build();
		ArticleEntity articleEntity3 = ArticleEntity.builder().name("test3").description("test3").price(new BigDecimal(1)).build();
		articleRepository.save(articleEntity2);
		articleRepository.save(articleEntity3);
		stock.getArticle().add(articleEntity2);
		stock.setQuantity(5);
		stock1.getArticle().add(articleEntity3);
		stock1.setQuantity(51);
		stockRepository.save(stock);
		stockRepository.save(stock1);

		//then
		AllStockDto allStockDto = stockService.getAllStock();
		//then
		Assertions.assertThat(allStockDto.getStock().size()).isEqualTo(2);
	}
}
