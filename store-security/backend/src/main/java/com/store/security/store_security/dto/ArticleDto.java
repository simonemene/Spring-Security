package com.store.security.store_security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArticleDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime tmstInsert;

}
