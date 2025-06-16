package com.store.security.store_security.controller;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

	private final IOrderService orderService;

	@PostMapping("/addMultipleOrder")
	public ResponseEntity<String> addMultipleOrder(@RequestBody List<ArticleDto> articleDto)
			throws OrderException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(orderService.addOrder(articleDto,authentication.getName()))
		{
			return ResponseEntity.status(HttpStatus.OK).body("Add order");
		}
		throw new OrderException("Error, not add order");
	}
}
