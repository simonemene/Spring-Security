package com.store.security.store_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc
public class UserControllerIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "prova@gmail.com", roles = "USER")
	public void userFound() throws Exception {
		//given

		String username = "prova@gmail.com";
		UserEntity userEntity = UserEntity.builder().username("prova@gmail.com").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build();
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").user(userEntity).build();
		userEntity.setAuthoritiesList(List.of(authoritiesEntity));
		userRepository.save(userEntity);
		UserDto userDto = UserDto.builder().username(username).age(21).build();
		String json = objectMapper.writeValueAsString(userDto);
		//whe
		//then
		mockMvc.perform(get("/api/user/getUserDetails/{username}",username))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(json));
	}

	@Test
	@WithMockUser(username = "prova@gmail.com", roles = "USER")
	public void userNoAccess() throws Exception {
		//given

		String username = "anakin@gmail.com";
		UserEntity userEntity = UserEntity.builder().username("prova@gmail.com").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build();
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").user(userEntity).build();
		userEntity.setAuthoritiesList(List.of(authoritiesEntity));
		userRepository.save(userEntity);
		UserDto userDto = UserDto.builder().username(username).age(21).build();
		String json = objectMapper.writeValueAsString(userDto);
		//when
		//then
		mockMvc.perform(get("/api/user/getUserDetails/{username}",username))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Access Denied"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/user/getUserDetails/anakin@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(HttpStatus.FORBIDDEN.getReasonPhrase()));
	}

	@Test
	@WithMockUser(username = "prova@gmail.com", roles = "TRACKER")
	public void userNoAccessRole() throws Exception {
		//given

		String username = "prova@gmail.com";
		UserEntity userEntity = UserEntity.builder().username("prova@gmail.com").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build();
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").user(userEntity).build();
		userEntity.setAuthoritiesList(List.of(authoritiesEntity));
		userRepository.save(userEntity);
		UserDto userDto = UserDto.builder().username(username).age(21).build();
		String json = objectMapper.writeValueAsString(userDto);
		//when
		//then
		mockMvc.perform(get("/api/user/getUserDetails/{username}",username))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Access Denied"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.FORBIDDEN.value()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/user/getUserDetails/prova@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(HttpStatus.FORBIDDEN.getReasonPhrase()));
	}
}
