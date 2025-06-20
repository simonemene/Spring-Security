package com.store.security.store_security.controller;

import com.store.security.store_security.controladvice.GenericExceptionHandler;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//TODO:not real UNIT TEST

@WebMvcTest(UserController.class)
@Import(GenericExceptionHandler.class)
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;


	@Test
	@WithMockUser(username = "admin@gmail.com", roles = "USER")
	public void userNotFound() throws Exception {
		//given
		String username = "admin@gmail.com";
		Mockito.when(userService.findUser(username)).thenThrow(new UserException("User admin not found"));
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getUserDetails/{username}",username))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("User admin not found"));
	}
}
