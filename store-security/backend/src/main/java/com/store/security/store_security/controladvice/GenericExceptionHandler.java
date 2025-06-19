package com.store.security.store_security.controladvice;

import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.exceptions.StockException;
import com.store.security.store_security.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<String> controlOrderFailed(OrderException orderException)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderException.getMessage());
	}


	@ExceptionHandler(ArticleException.class)
	public ResponseEntity<String> controlArticleFailed(ArticleException articleException)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				articleException.getMessage());
	}


	@ExceptionHandler(StockException.class)
	public ResponseEntity<String> controlStockFailed(StockException stockException)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stockException.getMessage());
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> controlUserFailed(UserException userException)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userException.getMessage());
	}


}
