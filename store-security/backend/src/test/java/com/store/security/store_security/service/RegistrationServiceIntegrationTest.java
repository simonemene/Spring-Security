package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class RegistrationServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Test
	public void registration()
	{
		//given
		UserDto userDto = UserDto.builder().username("username").age(29).tmstInsert(
				LocalDateTime.of(2022, 1, 1, 1, 1)).password("1234").build();
		//when
		UserDto result = registrationService.registrationUser(userDto);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
		Assertions.assertThat(result).usingRecursiveComparison().ignoringFields("id","password").isEqualTo(userDto);
	}


}
