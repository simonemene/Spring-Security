package com.store.security.store_security.dto;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockArticleDto {

    private Long id;

    private StockDto stock;

    private ArticleDto article;

    private int quantity;
}
