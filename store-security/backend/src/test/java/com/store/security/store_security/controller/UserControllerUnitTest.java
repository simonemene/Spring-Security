package com.store.security.store_security.controller;

import com.store.security.store_security.controladvice.GenericExceptionHandler;
import com.store.security.store_security.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import(GenericExceptionHandler.class)
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private IUserService userService;


	@Test
	@WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
	public void userNotFound()
	{
		//given
		String username = "admin@gmail.com";
		//when
		//then
	}
}
