package com.store.security.store_security.controller;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final IUserService userService;


	@GetMapping("/getUserDetails/{username}")
	public ResponseEntity<UserDto> userDetails(@PathVariable("username") String username)
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(username));
	}
}
