package com.store.security.store_security.mapper;

import com.store.security.store_security.dto.StockArticleDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.StockArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockArticleMapper {

    StockArticleEntity toEntity(StockArticleDto stockDto);

    StockArticleDto toDto(StockArticleEntity stockEntity);
}
