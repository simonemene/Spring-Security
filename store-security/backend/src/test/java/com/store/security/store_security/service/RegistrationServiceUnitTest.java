package com.store.security.store_security.service;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.mapper.UserMapper;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RegistrationServiceUnitTest {

	@Mock
	private UserRepository userRepository;;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserMapper userMapper;

	@Mock
	private AuthoritiesRepository authoritiesRepository;

	@InjectMocks
	private RegistrationService registrationService;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		registrationService = new RegistrationService(userRepository, userMapper, passwordEncoder, authoritiesRepository);
	}

	@Test
	public void registration()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		UserEntity userEntity = UserEntity.builder().password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		UserEntity userEntityReturn = UserEntity.builder().id(1).password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();

		Mockito.when(userMapper.toEntity(userDto)).thenReturn(userEntity);
		Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("1234");
		Mockito.when(userRepository.save(userEntity)).thenReturn(userEntityReturn);
		//when
		Map<String,Boolean> registration = registrationService.registrationUser(userDto);
		//then
		Assertions.assertThat(registration.get("Registration successful")).isTrue();
	}

	@Test
	public void registrationAgeFailed()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(17).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		//when
		Map<String,Boolean> registration = registrationService.registrationUser(userDto);
		//then
		Assertions.assertThat(registration.get("Age must be at least 18")).isFalse();
	}

	@Test
	public void registrationAlreadyExistsFailed()
	{
		//given
		UserDto userDto = UserDto.builder().password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		UserEntity userEntity = UserEntity.builder().id(1).password("1234").age(18).username("username1").tmstInsert(
				LocalDateTime.now()).authoritiesList(
				List.of(AuthoritiesEntity.builder().authority("ROLE_USER").build())).build();
		Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

		Mockito.when(userRepository.findByUsername("username1")).thenReturn(userEntityOptional);
		//when
		Map<String,Boolean> registration = registrationService.registrationUser(userDto);
		//then
		Assertions.assertThat(registration.get("User already exists")).isFalse();
	}


}
