package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.*;
import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.mapper.StockMapper;
import com.store.security.store_security.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class OrderServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleMapper articleMapper;


	@Test
	public void addOrder() throws OrderException {
		//given
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", "password"));
		UserEntity user = UserEntity.builder().username("username").password("1234")
				.age(23).tmstInsert(LocalDateTime.now()).authoritiesList(new ArrayList<>()).build();
		userRepository.save(user);

		Map<ArticleDto,Integer> articles = new HashMap<>();

		ArticleDto article1 = ArticleDto.builder().name("car").tmstInsert(LocalDateTime.now())
				.description("test").price(new BigDecimal(10)).build();
		ArticleDto article2 = ArticleDto.builder().name("table").tmstInsert(LocalDateTime.now())
				.description("test1").price(new BigDecimal(15)).build();
		articles.put(article1,3);
		articles.put(article2,31);
		articleRepository.save(articleMapper.toEntity(article1));
		articleRepository.save(articleMapper.toEntity(article2));

		ArticlesOrderDto articlesOrderDto = ArticlesOrderDto.builder().articles(articles).username("username").build();
		//when
		articlesOrderDto = orderService.orderArticles(articlesOrderDto);
		//then
		Assertions.assertThat(articlesOrderDto).isNotNull();
		Assertions.assertThat(articlesOrderDto.getArticles()).isNotNull();
		Assertions.assertThat(articlesOrderDto.getArticles().size()).isEqualTo(2);

		ArticleDto checkArticle1 = articlesOrderDto.getArticles().entrySet().stream()
				.filter(value-> value.getValue() == 3)
				.map(Map.Entry::getKey).findFirst().orElseThrow();

		Assertions.assertThat(checkArticle1.getId()).isGreaterThan(0);
		Assertions.assertThat(articles.entrySet().stream()
				.filter(check-> check.getValue() == 3).findFirst().get().getKey())
				.usingRecursiveComparison().ignoringFields("price","id","description","tmstInsert").isEqualTo(checkArticle1);


	}





}
