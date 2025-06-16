package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

	ArticleEntity toEntity(ArticleDto articleDto);

	ArticleDto toDto(ArticleEntity articleEntity);
}
