package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;

public interface IStockService {

	public StockEntity getStockByArticle(ArticleEntity article);

	public boolean saveStock(ArticleEntity article);

	public boolean decrementArticle(ArticleEntity article,int quantity);
}
