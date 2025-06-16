package com.store.security.store_security.controller;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.service.IRegistrationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AuthenticationControllerUnitTest {


	@Mock
	private IRegistrationService registrationService;

	private AuthenticationController authenticationController;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		authenticationController = new AuthenticationController(registrationService);
	}


	@Test
	public void registration()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(21).username("username").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		Map<String,Boolean> response = Map.of("Registration successful",true);
		Mockito.when(registrationService.registrationUser(userDto)).thenReturn(response);
		//when
		ResponseEntity<String> responseEntity = authenticationController.registration(userDto);
		//then
		Mockito.verify(registrationService,Mockito.times(1)).registrationUser(Mockito.any());
		Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		Assertions.assertThat(responseEntity.getBody()).isEqualTo("Registration successful");
	}
}
