package com.store.security.store_security.entity.key;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderLineKeyEmbeddable implements Serializable {

    private long idOrder;

    private long idArticle;

}
