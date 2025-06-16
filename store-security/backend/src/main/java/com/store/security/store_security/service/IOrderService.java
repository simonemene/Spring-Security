package com.store.security.store_security.service;

import com.store.security.store_security.exceptions.OrderException;

public interface IOrderService {

	public boolean addOrder(int articleId,String username) throws OrderException;
}
