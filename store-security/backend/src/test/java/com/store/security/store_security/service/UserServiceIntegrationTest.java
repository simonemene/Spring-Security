package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class UserServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private IUserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;


	@Test
	public void findUser()
	{
		//given
		UserEntity userEntity = UserEntity.builder().username("username").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build();
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").user(userEntity).build();
		userEntity.setAuthoritiesList(List.of(authoritiesEntity));
		userRepository.save(userEntity);

		//when
		UserDto user = userService.findUser(userEntity.getUsername());
		//then
		Assertions.assertThat(user.getPassword()).isEqualTo(userEntity.getPassword());
		Assertions.assertThat(user.getUsername()).isEqualTo(userEntity.getUsername());
		Assertions.assertThat(user.getAuthoritiesList().getFirst().getAuthority()).isEqualTo(userEntity.getAuthoritiesList().getFirst().getAuthority());
		Assertions.assertThat(user.getAge()).isEqualTo(userEntity.getAge());
		Assertions.assertThat(user.getTmstInsert()).isEqualTo(userEntity.getTmstInsert());
	}

	@Test
	public void userNotFound()
	{
		//given


		UserEntity userEntity = UserEntity.builder().username("username").age(21).password("1234").tmstInsert(
				LocalDateTime.of(2022, 1, 1, 0, 0)).build();
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").user(userEntity).build();
		userEntity.setAuthoritiesList(List.of(authoritiesEntity));
		//when
		//then
		Assertions.assertThatThrownBy(()->userService.findUser(userEntity.getUsername()))
				.isInstanceOf(UserException.class)
				.hasMessageContaining("User username not found");
	}
}
