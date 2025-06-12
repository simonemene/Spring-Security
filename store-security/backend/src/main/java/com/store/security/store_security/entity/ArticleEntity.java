package com.store.security.store_security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Table(name="article")
@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "tmst_insert", nullable = false)
    private LocalDateTime tmstInsert;

}
