package com.store.security.store_security.controller;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest extends
		StoreSecurityApplicationTests {

	@Autowired
	private AuthenticationController authenticationController;


	@Test
	public void registration()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(21).username("username").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		//when
		ResponseEntity<String> responseEntity = authenticationController.registration(userDto);
		//then
		Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		Assertions.assertThat(responseEntity.getBody()).isEqualTo("Registration successful");
	}

	@Test
	public void registrationAgeFailed()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(17).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		//when
		ResponseEntity<String> responseEntity = authenticationController.registration(userDto);
		//then
		Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(
				HttpStatus.BAD_REQUEST.value());
		Assertions.assertThat(responseEntity.getBody()).isEqualTo("Age must be at least 18");
	}

	@Test
	public void registrationUserAlreadyExists()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		UserDto userDtoAlreadyExist = UserDto.builder().password("1234").age(25).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		//when
		authenticationController.registration(userDto);
		ResponseEntity<String> responseEntityAlreadyExist = authenticationController.registration(userDtoAlreadyExist);
		//then
		Assertions.assertThat(responseEntityAlreadyExist.getStatusCode().value()).isEqualTo(
				HttpStatus.BAD_REQUEST.value());
		Assertions.assertThat(responseEntityAlreadyExist.getBody()).isEqualTo("User already exists");
	}


	@Test
	public void registrationFailed()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(25).tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		//when
		ResponseEntity<String> responseEntity = authenticationController.registration(userDto);
		//then
		Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(
				HttpStatus.BAD_REQUEST.value());
		Assertions.assertThat(responseEntity.getBody()).isEqualTo("Registration failed");
	}



}
