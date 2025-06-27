package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AllUserDto {

	public List<UserDto> users;

	public void addUser(UserDto user)
	{
		if(null == users)
		{
			users = new ArrayList<>();
			users.add(user);
		}else
		{
			users.add(user);
		}
	}
}
