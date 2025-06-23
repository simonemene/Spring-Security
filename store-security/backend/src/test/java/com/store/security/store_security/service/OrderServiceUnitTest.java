package com.store.security.store_security.service;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.OrderEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderServiceUnitTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private StockArticleRepository stockArticleRepository;

	@Mock
	private TrackRepository trackRepository;

	@Mock
	private OrderLineRepository orderLineRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ArticleMapper articleMapper;

	@Mock
	private ArticleRepository articleRepository;

	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		orderService = new OrderService(orderRepository,stockArticleRepository,
				trackRepository,orderLineRepository,userRepository,
				articleMapper,articleRepository);
	}

	@Test
	public void orderFailArticles() throws OrderException {
		//given
		ArticleDto articleDto = ArticleDto.builder().name("car").build();
		Map<ArticleDto,Integer> articles = Map.of(articleDto,1);
		ArticlesOrderDto articlesOrderDto = ArticlesOrderDto.builder().username("username").articles(articles).build();
		Mockito.when(articleRepository.findByName(Mockito.anyString())).thenReturn(
				ArticleEntity.builder().name("car").id(0L).build());

		//when
		//then
		Assertions.assertThatThrownBy(()->orderService.orderArticles(articlesOrderDto))
				.isInstanceOf(ArticleException.class)
				.hasMessageContaining(String.format("[ARTICLE: %s] NOT FOUND",articleDto.getName()));
	}

	@Test
	public void orderArticles() throws OrderException {
		//given
		UserEntity user = UserEntity.builder().username("username").build();
		Mockito.when(userRepository.findByUsername("username")).thenReturn(
				Optional.ofNullable(user));

		ArticleDto articleDto = ArticleDto.builder().name("car").tmstInsert(LocalDateTime.of(2022,1,1,1,1)).build();
		Map<ArticleDto,Integer> articles = new HashMap<>();
		articles.put(articleDto,2);
		ArticlesOrderDto articlesOrderDto = ArticlesOrderDto.builder().username("username").articles(articles).build();
		Mockito.when(articleRepository.findByName("car")).thenReturn(
				ArticleEntity.builder().name("car").id(1L).build());

		Mockito.when(orderRepository.save(Mockito.any()))
				.thenReturn(OrderEntity.builder().id(0L).build());

		//when
		//then
		Assertions.assertThatThrownBy(()->orderService.orderArticles(articlesOrderDto))
				.isInstanceOf(OrderException.class)
				.hasMessageContaining(String.format("[USER: %s ARTICLES: %s] ORDER NOT SAVED","username","[car]"));
	}

}