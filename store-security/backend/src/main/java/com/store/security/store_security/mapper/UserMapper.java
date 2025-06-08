package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source="authorities",target="authorities",qualifiedByName="setAuthoritiesDto")
    UserEntity toEntity(UserDto userDto);

    @Mapping(source="authorities",target="authorities",qualifiedByName="setAuthoritiesEntity")
    UserDto toDto(UserEntity userEntity);

    @Named("setAuthoritiesDto")
    default Set<GrantedAuthority> setAuthoritiesDto(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Named("setAuthoritiesEntity")
    default Set<String> setAuthoritiesEntity(Set<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
