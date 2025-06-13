package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockService implements IStockService{

	private final StockRepository stockRepository;

	private final ArticleRepository articleRepository;

	@Override
	public StockEntity getStockByArticle(ArticleEntity article) {
		Optional<StockEntity> stockEntity = stockRepository.findByArticle(article);
		return stockEntity.orElseThrow(()-> new EntityNotFoundException("Article not found"));
	}

	@Transactional
	@Override
	public boolean saveStock(ArticleEntity article) {
		boolean result = false;
		ArticleEntity articleEntity = articleRepository.findById(Integer.parseInt(String.valueOf(article.getId())))
				.orElseThrow(()-> new EntityNotFoundException("Stock not found for article id " + article.getId()));

		StockEntity stockEntity = new StockEntity();
		stockEntity.setArticle(articleEntity);
		stockEntity.setQuantity(0);
		return stockRepository.save(stockEntity).getId()>0;
	}

	@Transactional
	@Override
	public boolean decrementArticle(ArticleEntity article, int quantity) {
		StockEntity stockEntity = getStockByArticle(article);
		if(stockEntity.getQuantity()-quantity<0)
		{
			return false;
		}
		stockEntity.setQuantity(stockEntity.getQuantity()-quantity);
		return stockRepository.save(stockEntity).getId()>0;
	}
}
