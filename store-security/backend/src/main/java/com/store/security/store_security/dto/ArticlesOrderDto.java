package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ArticlesOrderDto {

	private Map<ArticleDto, Integer> articles;

	private Long idOrder;

	private String username;
}
