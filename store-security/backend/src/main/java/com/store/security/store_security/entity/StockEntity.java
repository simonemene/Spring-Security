package com.store.security.store_security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
@Entity
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER)
    private List<ArticleEntity> article;

    private int quantity;

    private boolean addArticle(ArticleEntity articleEntity)
    {
        if(null == article)
        {
            article = new ArrayList<>();
        }
        return article.add(articleEntity);
    }
}
