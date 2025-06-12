package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.UserDto;
import com.store.security.store_security.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDto userDto);

    UserDto toDto(UserEntity userEntity);
}
