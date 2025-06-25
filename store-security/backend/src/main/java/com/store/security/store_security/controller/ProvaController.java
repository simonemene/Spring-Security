package com.store.security.store_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prova")
public class ProvaController {

	@GetMapping
	public ResponseEntity<String> prova()
	{
		return ResponseEntity.ok("prova");
	}
}
