package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.OrderEntity;
import com.store.security.store_security.entity.OrderLineEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class OrderServiceUnitTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderLineRepository orderLineRepository;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleMapper articleMapper;

	@InjectMocks
	private OrderService orderService;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		orderService = new OrderService(orderRepository, articleRepository, stockRepository, userRepository, orderLineRepository, articleMapper);
	}

	@Test
	public void getOrder()
	{
		//given
		UserEntity user = UserEntity.builder().age(21).username("test").build();
		OrderEntity order = OrderEntity.builder().user(user).tmstInsert(LocalDateTime.of(2022, 1, 1, 1, 1)).build();
		ArticleEntity article = ArticleEntity.builder().name("test").description("test").build();
		OrderLineKeyEmbeddable orderLineKey = OrderLineKeyEmbeddable.builder().idOrder(order.getId()).idArticle(article.getId()).build();
		OrderLineEntity orderLine = OrderLineEntity.builder().id(orderLineKey).article(article).order(order).build();

		//TODO:MOCKITO
		//when

		//then
	}
}
