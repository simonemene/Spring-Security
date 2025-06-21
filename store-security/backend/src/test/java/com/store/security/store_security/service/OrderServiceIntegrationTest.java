package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.*;
import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.mapper.StockMapper;
import com.store.security.store_security.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private IStockService stockService;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ArticleRepository articleRepository;


	@Test
	public void getAllStock()
	{
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1)).build();
		ArticleEntity articleEntity1 = ArticleEntity.builder().name("test1").description("test1").price(new BigDecimal(4)).build();
		ArticleEntity articleEntity2 = ArticleEntity.builder().name("test2").description("test2").price(new BigDecimal(3)).build();
        articleRepository.save(articleEntity);
		articleRepository.save(articleEntity1);
		articleRepository.save(articleEntity2);

		ArticleEntity articleEntity3 = ArticleEntity.builder().name("test3").description("test3").price(new BigDecimal(1)).build();
		ArticleEntity articleEntity4 = ArticleEntity.builder().name("test4").description("test4").price(new BigDecimal(2)).build();
		ArticleEntity articleEntity5 = ArticleEntity.builder().name("test5").description("test5").price(new BigDecimal(9)).build();
		articleRepository.save(articleEntity3);
		articleRepository.save(articleEntity4);
		articleRepository.save(articleEntity5);

		StockEntity stockEntity = StockEntity.builder().article(List.of(articleEntity,articleEntity1)).quantity(1).build();
		StockEntity stockEntity1 = StockEntity.builder().article(List.of(articleEntity3,articleEntity4)).quantity(1).build();
		StockEntity stockEntitySaved = stockRepository.save(stockEntity);
		StockEntity stockEntity1Saved = stockRepository.save(stockEntity1);
		stockEntitySaved.getArticle().add(articleEntity2);
		stockEntitySaved.setQuantity(5);
		stockEntity1Saved.getArticle().add(articleEntity5);
		stockEntity1Saved.setQuantity(5);
		stockRepository.save(stockEntitySaved);
		stockRepository.save(stockEntity1Saved);

		//when
		AllStockDto allStock = stockService.getAllStock();
		//then
		Assertions.assertThat(allStock.getStock().size()).isEqualTo(2);
	}



}
