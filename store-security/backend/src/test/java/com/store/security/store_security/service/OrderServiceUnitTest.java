package com.store.security.store_security.service;

import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
		//when
		//then
	}
}
