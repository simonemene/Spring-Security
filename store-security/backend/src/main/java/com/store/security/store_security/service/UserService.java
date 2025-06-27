package com.store.security.store_security.service;

import com.store.security.store_security.dto.AllUserDto;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.mapper.UserMapper;
import com.store.security.store_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	@Transactional(readOnly = true)
	@Override
	public UserDto findUser(String username) {
		Optional<UserEntity> userEntity = userRepository.findByUsername(username);
		if(userEntity.isPresent())
		{
			return userMapper.toDto(userEntity.get());
		}
		throw new UserException(String.format("User %s not found", username));
	}

	@Transactional(readOnly = true)
	@Override
	public AllUserDto allUser() {
		List<UserEntity> users = userRepository.findAll();
		AllUserDto allUser = AllUserDto.builder().build();
		if (!users.isEmpty()) {
			for(UserEntity user : users)
			{
				allUser.addUser(userMapper.toDto(user));
			}
		}else
		{
			throw new UserException("No user found");
		}
		return allUser;
	}

}
