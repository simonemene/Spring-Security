package com.store.security.store_security.service;

import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	public void orderArticles()
	{
		//given

		//when

		//then
	}

}