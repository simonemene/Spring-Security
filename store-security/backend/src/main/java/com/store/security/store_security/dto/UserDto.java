package com.store.security.store_security.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.security.store_security.entity.AuthoritiesEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserDto {

    private Long id;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int age;

    private List<String> authoritiesList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime tmstInsert;
}
