package com.store.security.store_security.service;

import com.store.security.store_security.dto.AllUserDto;
import com.store.security.store_security.dto.UserDto;

public interface IUserService {

	public UserDto findUser(String username);

	public AllUserDto allUser();
}
