package com.store.security.store_security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc
public class UserControllerIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "prova@gmail.com", roles = "USER")
	@Test
	public void userFound() throws Exception {
		//given
		String username = "prova@gmail.com";
		Authentication authentication = new UsernamePasswordAuthenticationToken(username,"",
				List.of(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDto userDto = UserDto.builder().username(username).age(21).build();
		String json = objectMapper.writeValueAsString(userDto);
		//when
		//then
		mockMvc.perform(get("/api/user/getUserDetails/{username}",username))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(json));
	}
}
