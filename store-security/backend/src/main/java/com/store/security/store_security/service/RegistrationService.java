package com.store.security.store_security.service;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.mapper.UserMapper;
import com.store.security.store_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegistrationService implements IRegistrationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Boolean> registrationUser(UserDto userDto) {

        UserEntity userRegister = null;
        try {

            if (userDto.getUsername() != null && userDto.getPassword() != null) {
                Optional<UserEntity> userCheck = userRepository.findByUsername(userDto.getUsername());
                if (userCheck.isPresent() && userCheck.get().getId()>0) {
                    return Map.of("User already exists", false);
                }
                if (userDto.getAge() < 18) {
                    return Map.of("Age must be at least 18", false);
                }
                userDto.setTmstInsert(LocalDateTime.now());
                userDto.setAuthorities(Collections.singleton("ROLE_USER"));
                UserEntity userEntity = userMapper.toEntity(userDto);
                userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRegister = userRepository.save(userEntity);
            }
        } catch (Exception e) {
            return Map.of("Registration failed", false);
        }
        return null != userRegister && userRegister.getId() > 0 ? Map.of("Registration successful", true) : Map.of("Registration failed", false);
    }
}
