package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class UserSecurityDetailsServiceIntegrationTest extends
		StoreSecurityApplicationTests {

	@Autowired
	private UserSecurityDetailService userSecurityDetailsService;

	@Autowired
	private UserRepository userRepository;


	@Test
	public void userFound()
	{
		//given
		AuthoritiesEntity authoritiesEntity = new AuthoritiesEntity();
		authoritiesEntity.setAuthority("ROLE_USER");
		UserEntity user = new UserEntity();
		user.setUsername("username");
		user.setPassword("password");
		user.setTmstInsert(LocalDateTime.of(2022, 1, 1, 0, 0));
		user.setAuthoritiesList(List.of(authoritiesEntity));
		authoritiesEntity.setUser(user);
		UserEntity savedUser = userRepository.save(user);
		//when
		UserDetails userDetails = userSecurityDetailsService.loadUserByUsername("username");
		//then
		Assertions.assertThat(savedUser.getUsername()).isEqualTo(userDetails.getUsername());
		Assertions.assertThat(savedUser.getPassword()).isEqualTo(userDetails.getPassword());
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		String ruolo = authorities.stream().findFirst().get().getAuthority();
		Assertions.assertThat(savedUser.getAuthoritiesList().getFirst().getAuthority()).isEqualTo(ruolo);

	}

	@Test
	public void userNotFound()
	{
		//given
		//when
		//then
        Assertions.assertThatThrownBy(()->userSecurityDetailsService.loadUserByUsername("username1"))
				.isInstanceOf(UsernameNotFoundException.class).hasMessageContaining("User not found");
	}
}
