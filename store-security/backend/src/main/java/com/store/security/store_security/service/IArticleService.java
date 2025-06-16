package com.store.security.store_security.service;

import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.ArticleEntity;

public interface IArticleService {

    public boolean saveArticle(ArticleDto articleDto);

    public boolean deleteArticle(long id);

    public boolean saveArticleQuantity(long id, int quantity);
}
