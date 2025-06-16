package com.store.security.store_security.controladvice;

import com.store.security.store_security.exceptions.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<String> controlOrderFailed(OrderException orderException)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not added");
	}


}
