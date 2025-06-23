package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "authoritiesList", source = "authoritiesList", qualifiedByName = "mapAuthoritiesToStrings")
    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "authoritiesList", source = "authoritiesList", qualifiedByName = "mapStringsToAuthorities")
    UserEntity toEntity(UserDto userDto);

    @Named("mapAuthoritiesToStrings")
    static List<String> mapAuthoritiesToStrings(List<AuthoritiesEntity> authorities) {
        if (authorities == null) return null;
        return authorities.stream()
                .map(AuthoritiesEntity::getAuthority)
                .toList();
    }

    @Named("mapStringsToAuthorities")
    static List<AuthoritiesEntity> mapStringsToAuthorities(List<String> authorities) {
        if (authorities == null) return null;
        return authorities.stream()
                .map(auth -> {
                    AuthoritiesEntity a = new AuthoritiesEntity();
                    a.setAuthority(auth);
                    return a;
                })
                .toList();
    }
}

