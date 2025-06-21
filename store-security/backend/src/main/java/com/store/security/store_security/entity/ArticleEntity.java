package com.store.security.store_security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="article")
@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @Column(name = "tmst_insert", nullable = false)
    private LocalDateTime tmstInsert;

    @ManyToOne
    @JoinColumn(name = "id_stock", nullable = false)
    private StockEntity stock;

}
