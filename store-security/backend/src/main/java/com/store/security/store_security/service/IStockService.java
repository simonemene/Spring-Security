package com.store.security.store_security.service;

import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;

import java.util.List;

public interface IStockService {

	public AllStockDto getAllStock();

	public StockDto getStockByArticle(Long idArticle);

	public StockDto loadArticle(StockDto stockDto);

	public StockDto decrementArticle(Long id,Integer quantity);

	public StockDto saveArticleQuantity(Long id, Integer quantity);

}
