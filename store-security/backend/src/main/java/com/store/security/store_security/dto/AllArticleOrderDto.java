package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllArticleOrderDto {

	private ArticleDto articleDto;

	private Integer quantity;
}
