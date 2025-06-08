package com.store.security.store_security.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserDto {

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private int age;

    private Set<String> authorities;

    private LocalDateTime tmstInsert;
}
