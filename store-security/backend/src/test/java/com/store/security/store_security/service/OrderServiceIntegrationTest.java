package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.*;
import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
import com.store.security.store_security.enums.StatusTrackEnum;
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
	private OrderRepository orderRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private TrackRepository trackRepository;


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

		Iterable<OrderEntity> order = orderRepository.findAll();
		Assertions.assertThat(order).hasSize(1);
		OrderEntity orderEntity = order.iterator().next();
		Assertions.assertThat(orderEntity.getUser()).isEqualTo("username");


		Iterable<OrderLineEntity> orderLine = orderLineRepository.findAll();
		Assertions.assertThat(orderLine).hasSize(2);
		ArrayList<OrderLineEntity> orderLineEntity = Arrays.asList(orderLine.iterator().next());
		orderLineEntity.add(orderLine.iterator().next());
		Assertions.assertThat().isEqualTo("username");

		Iterable<TrackEntity> trackLine = trackRepository.findAll();
		Assertions.assertThat(trackLine).hasSize(1);
		TrackEntity trackEntity = trackLine.iterator().next();
		Assertions.assertThat(trackEntity.getStatus()).isEqualTo(
				StatusTrackEnum.ORDER_PLACED);

	}

	@Test
	public void addOrderFailedNoUser() throws OrderException {
		//given
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", "password"));

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
		//then
		Assertions.assertThatThrownBy(()->orderService.orderArticles(articlesOrderDto))
				.isInstanceOf(UserException.class)
				.hasMessageContaining(String.format("[USER OBJECT %s USER SECURITY %s] NOT FOUND","username","username"));

		Iterable<OrderEntity> order = orderRepository.findAll();
		Assertions.assertThat(order).hasSize(0);
		Iterable<OrderLineEntity> orderLine = orderLineRepository.findAll();
		Assertions.assertThat(orderLine).hasSize(0);
	}


	@Test
	public void addOrderFailedArticlesQuantity() throws OrderException {
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
		articles.put(article1,null);
		articles.put(article2,31);
		articleRepository.save(articleMapper.toEntity(article1));
		articleRepository.save(articleMapper.toEntity(article2));

		ArticlesOrderDto articlesOrderDto = ArticlesOrderDto.builder().articles(articles).username("username").build();
		//when
		//then
		Assertions.assertThatThrownBy(()->orderService.orderArticles(articlesOrderDto))
				.isInstanceOf(OrderException.class)
				.hasMessageContaining(String.format("[ARTICLE: %s QUANTITY: %s] INVALID","car",null));

		Iterable<OrderEntity> order = orderRepository.findAll();
		Assertions.assertThat(order).hasSize(0);
		Iterable<OrderLineEntity> orderLine = orderLineRepository.findAll();
		Assertions.assertThat(orderLine).hasSize(0);
	}







}
