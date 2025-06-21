package com.store.security.store_security.dto;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockArticleDto {

    private Long id;

    private StockEntity stock;

    private ArticleEntity article;

    private int quantity;
}
