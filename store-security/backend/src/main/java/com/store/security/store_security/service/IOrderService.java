package com.store.security.store_security.service;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.exceptions.OrderException;

import java.util.List;

public interface IOrderService {

	public boolean addOrder(List<ArticleDto> articleId,String username) throws OrderException;

	public List<ArticleDto> getOrders(String username) throws OrderException;
}
