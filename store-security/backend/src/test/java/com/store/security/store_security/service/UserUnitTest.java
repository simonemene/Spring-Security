package com.store.security.store_security.service;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.mapper.UserMapper;
import com.store.security.store_security.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class UserUnitTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserService userService;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		userService = new UserService(userRepository, userMapper);
	}


	@Test
	public void userFound()
	{
		//given
		Optional<UserEntity> userEntity = Optional.of(UserEntity.builder().username("username").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build());
		Mockito.when(userRepository.findByUsername("username")).thenReturn(userEntity);
		UserDto userDto = UserDto.builder().username("username").age(21).password("1234").build();
		Mockito.when(userMapper.toDto(userEntity.get())).thenReturn(userDto);
		//when
		UserDto result = userService.findUser("username");
		//then
		Assertions.assertThat(userDto).usingRecursiveComparison().isEqualTo(result);
	}

	@Test
	public void userNotFound()
	{
		//given
		Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
		//when
		//then
		Assertions.assertThatThrownBy(()->userService.findUser("username"))
				.isInstanceOf(UserException.class)
				.hasMessageContaining("User username not found");
	}

	@Test
	public void allUserNotFound()
	{
		//given
		Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
		//when
		//then
		Assertions.assertThatThrownBy(()->userService.allUser())
				.isInstanceOf(UserException.class)
				.hasMessageContaining("No user found");
	}

}
