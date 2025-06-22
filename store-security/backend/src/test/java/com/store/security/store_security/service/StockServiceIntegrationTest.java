package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StockServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private StockArticleRepository stockArticleRepository;

	@Test
	public void getAllStock()
	{
		//given
		StockEntity stockEntity = StockEntity.builder().build();
		ArticleEntity articleEntity = ArticleEntity.builder().name("car").price(new BigDecimal(1)).description("card description")
				.tmstInsert(LocalDateTime.of(2025,12,1,1,1)).build();
		StockArticleEntity stockArticleEntity = StockArticleEntity.builder().article(articleEntity).quantity(10).build();
		stockEntity.setStockArticles(List.of(stockArticleEntity));
		stockArticleEntity.setStock(stockEntity);
		articleRepository.save(articleEntity);
		stockRepository.save(stockEntity);
		stockArticleRepository.save(stockArticleEntity);
		//when
		AllStockDto allStockDto = stockService.getAllStock();

		//then
	}

}
