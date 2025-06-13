package com.store.security.store_security.service;

import com.store.security.store_security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserSecurityDeatilsServiceTest {

	@InjectMocks
	private UserSecurityDetailService service;

	@Mock
	private UserRepository userRepository;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		service = new UserSecurityDetailService(userRepository);
	}


	@Test
	public void foundUser()
	{
		//given
		//when
		//then

	}
}
