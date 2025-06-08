package com.store.security.store_security.entity;

import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
import jakarta.persistence.*;

@Table(name = "order_line")
@Entity
public class OrderLineEntity {

    @EmbeddedId
    private OrderLineKeyEmbeddable id;

    @ManyToOne
    @MapsId("idOrder")
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    @ManyToOne
    @MapsId("idArticle")
    @JoinColumn(name = "id_article")
    private ArticleEntity article;

    private int price;

    private int quantity;
}
