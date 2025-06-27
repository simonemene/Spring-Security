package com.store.security.store_security.service;

import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.exceptions.OrderException;

public interface IOrderService {

	public ArticlesOrderDto orderArticles(ArticlesOrderDto articlesOrderDto)
			throws OrderException;

	public allOrderByUser(Integer id);


}
