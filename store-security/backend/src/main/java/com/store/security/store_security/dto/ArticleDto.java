package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime tmstInsert;

}
