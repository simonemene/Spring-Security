package com.store.security.store_security.controller;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.service.IRegistrationService;
import com.store.security.store_security.service.IUserService;
import com.store.security.store_security.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IRegistrationService registrationService;

    private final IUserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) {
        UserDto result = registrationService.registrationUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> userAuth()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

}
