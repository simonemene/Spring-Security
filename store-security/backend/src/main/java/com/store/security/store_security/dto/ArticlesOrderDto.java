package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ArticlesOrderDto {

	Map<ArticleDto, Integer> articles;

	private String username;
}
