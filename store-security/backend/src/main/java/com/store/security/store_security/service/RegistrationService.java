package com.store.security.store_security.service;

import com.store.security.store_security.constants.RoleConstants;
import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.mapper.UserMapper;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RegistrationService implements IRegistrationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthoritiesRepository authoritiesRepository;

    @Override
    public Map<String, Boolean> registrationUser(UserDto userDto) {

        UserEntity userRegister = null;


            if (userDto.getUsername() != null && userDto.getPassword() != null && !userDto.getUsername().isEmpty()) {
                Optional<UserEntity> userCheck = userRepository.findByUsername(userDto.getUsername());
                if (userCheck.isPresent() && userCheck.get().getId()>0) {
                    throw new UserException("User already exist");
                }
                if (userDto.getAge() < 18) {
                    throw new UserException("User must be at least 18 years old");
                }
                userDto.setTmstInsert(LocalDateTime.now());
                Optional<AuthoritiesEntity> authorities = authoritiesRepository.findByAuthority(RoleConstants.USER.getRole());
                userDto.setAuthoritiesList(authorities.map(Collections::singletonList).orElse(Collections.emptyList()));
                UserEntity userEntity = userMapper.toEntity(userDto);
                userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRegister = userRepository.save(userEntity);
            }
            else
            {
                throw new UserException("Registration failed");
            }

        return null != userRegister && userRegister.getId() > 0 ? Map.of("Registration successful", true) : Map.of("Registration failed", false);
    }
}
