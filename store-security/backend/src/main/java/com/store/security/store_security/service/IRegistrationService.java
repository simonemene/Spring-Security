package com.store.security.store_security.service;

import com.store.security.store_security.dto.UserDto;

import java.util.Map;

public interface IRegistrationService {

    public Map<String,Boolean> registrationUser(UserDto userDto);
}
