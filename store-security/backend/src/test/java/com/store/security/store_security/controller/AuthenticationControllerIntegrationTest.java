package com.store.security.store_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest extends
		StoreSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void registration() throws Exception {
		//given
		UserDto userDto = UserDto.builder().password("1234").age(21).username("username").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		String json = "{\"username\": \"username\",\n" + "  \"password\": \"1234\",\n" + "  \"age\": 21,\n"
				+ "  \"authoritiesList\": [\n" + "    {\n"
				+ "      \"authority\": \"ROLE_USER\"\n" + "    }\n" + "  ],\n"
				+ "  \"tmstInsert\": \"2025-06-18T00:00:00\"}";
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registration").contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Registration successful"));
	}

	@Test
	public void registrationAgeFailed() throws Exception {
		//given
		String json = "{\"username\": \"username\",\n" + "  \"password\": \"1234\",\n" + "  \"age\": 17,\n"
				+ "  \"authoritiesList\": [\n" + "    {\n"
				+ "      \"authority\": \"ROLE_USER\"\n" + "    }\n" + "  ],\n"
				+ "  \"tmstInsert\": \"2025-06-18T00:00:00\"}";
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registration").contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Age must be at least 18"));
	}

	@Test
	public void registrationUserAlreadyExists() throws Exception {
		//given
		UserDto userDto = UserDto.builder().password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		UserDto userDtoAlreadyExist = UserDto.builder().password("1234").age(25).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		String json = objectMapper.writeValueAsString(userDtoAlreadyExist);
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registration").contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Age must be at least 18"));
	}


	@Test
	public void registrationFailed() throws Exception {
		//given
		String json = "{\"username\": \"\",\n" + "  \"password\": \"1234\",\n" + "  \"age\": 21,\n"
				+ "  \"authoritiesList\": [\n" + "    {\n"
				+ "      \"authority\": \"ROLE_USER\"\n" + "    }\n" + "  ],\n"
				+ "  \"tmstInsert\": \"2025-06-18T00:00:00\"}";
		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registration").contentType("application/json").content(json))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Registration failed"));
	}



}
