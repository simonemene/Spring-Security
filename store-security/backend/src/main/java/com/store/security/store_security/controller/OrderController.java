package com.store.security.store_security.controller;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ListArticleDto;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final IOrderService orderService;

	@PostMapping
	public ResponseEntity<String> addMultipleOrder(@RequestBody List<ArticleDto> articleDto)
			throws OrderException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(orderService.addOrder(articleDto,authentication.getName()))
		{
			return ResponseEntity.status(HttpStatus.OK).body("Add order");
		}
		throw new OrderException("Error, not add order");
	}

	@PreAuthorize("#username == authentication.name && hasRole('ROLE_USER')")
	@GetMapping("/order/{username}")
	public ResponseEntity<ListArticleDto> getOrder(@PathVariable String username)
			throws OrderException {
		return ResponseEntity.status(HttpStatus.OK).body(ListArticleDto.builder().articles(orderService.getOrders(username)).build());
	}
}
