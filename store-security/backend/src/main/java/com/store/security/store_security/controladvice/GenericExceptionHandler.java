package com.store.security.store_security.controladvice;

import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.exceptions.StockException;
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


}
