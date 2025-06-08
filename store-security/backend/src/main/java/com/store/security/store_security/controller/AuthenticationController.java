package com.store.security.store_security.controller;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.service.IRegistrationService;
import com.store.security.store_security.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IRegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestParam UserDto userDto) {
        Map<String,Boolean> resultRegistration = registrationService.registrationUser(userDto);
        if(resultRegistration.values().iterator().next())
        {
            return ResponseEntity.ok(resultRegistration.keySet().iterator().next());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultRegistration.keySet().iterator().next());
    }

}
