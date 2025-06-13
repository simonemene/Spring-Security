package com.store.security.store_security.service;

import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class StockServiceUnitTest {

	@InjectMocks
	private StockService stockService;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private ArticleRepository articleRepository;


	@Test
	public void getStorckByArticle()
	{
		//given
		//when
		//then
	}
}
