package com.store.security.store_security.controller;

import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

	private final IOrderService orderService;



	@PostMapping("/addOrder/{id}")
	public ResponseEntity<String> addOrder(@PathVariable("id") int idArticle)
			throws OrderException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if(orderService.addOrder(idArticle,username))
		{
			return ResponseEntity.status(HttpStatus.OK).body("Add order");
		}
		throw new OrderException("Error, not add order");

	}
}
