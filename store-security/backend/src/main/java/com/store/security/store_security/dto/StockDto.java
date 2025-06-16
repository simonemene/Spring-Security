package com.store.security.store_security.dto;

import com.store.security.store_security.entity.ArticleEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDto {

	private ArticleEntity article;

	private int quantity;
}
