package com.store.security.store_security.service;

import com.store.security.store_security.dto.AllStockDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.StockException;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.mapper.StockMapper;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockService implements IStockService{

	private final StockRepository stockRepository;

	private final ArticleRepository articleRepository;

	private final StockMapper stockMapper;

	private final ArticleMapper articleMapper;


	@Override
	public AllStockDto getAllStock() {
		Iterable<StockEntity> allStock  = stockRepository.findAll();
		AllStockDto allStockDto = AllStockDto.builder().build();
        for (StockEntity stock : allStock) {
            allStockDto.getStock().add(stockMapper.toDto(stock));
        }
		if(!allStockDto.getStock().isEmpty())
		{
			return allStockDto;
		}
		throw new StockException("Stock not found");
	}

	@Override
	public StockDto getStockByArticle(Long idArticle) {
		StockEntity stock = articleRepository.findById(idArticle)
				.orElseThrow(()->new ArticleException(
						String.format("[ARTICLE: %s] Article not found",idArticle))).getStock();
		return stockMapper.toDto(stock);
	}

	@Transactional
	@Override
	public StockDto loadArticle(StockDto stock) {
		articleRepository.findById(stock.getArticle().getFirst().getId())
				.orElseThrow(()->new ArticleException(String.format("[ARTICLE: %s] Article exists",stock.getArticle().getFirst().getId())));
       StockEntity stockEntity = stockRepository.findById(stock.getId())
			   .orElseThrow(()->new StockException(String.format("[STOCK: %s] Stock not exists",stock.getId())));
		if(stock.getArticle().size()==1)
		{
			ArticleDto articleDto = stock.getArticle().getFirst();
			ArticleEntity articleEntity = articleRepository.save(articleMapper.toEntity(articleDto));
			stockEntity.getArticle().add(articleEntity);
			StockEntity savedStock = stockRepository.save(stockEntity);
			return stockMapper.toDto(savedStock);
		}
		throw new StockException("Stock not updated");
	}

	@Transactional
	@Override
	public StockDto decrementArticle(Long id,Integer quantity) {
		ArticleEntity article = articleRepository.findById(id)
				.orElseThrow(()->new ArticleException(String.format("[ARTICLE: %s] Article not found",id)));
		if(null != article.getStock() && article.getStock().getQuantity()>0 && (article.getStock().getQuantity() - quantity)>0) {
			StockEntity stockEntity = article.getStock();
			stockEntity.setQuantity(stockEntity.getQuantity()-quantity);
			stockEntity = stockRepository.save(stockEntity);
			if(stockEntity.getId()>0)
			{
				return stockMapper.toDto(stockEntity);
			}else
			{
				throw new StockException(String.format("[ARTICLE: %s QUANTITY: %s] Stock not updated",id,quantity));
			}
		}
		throw new StockException(String.format("[ARTICLE: %s QUANTITY: %s] Stock not updated",id,quantity));
	}

	@Transactional
	@Override
	public StockDto saveArticleQuantity(Long id, Integer quantity) {
		ArticleEntity article = articleRepository.findById(id)
				.orElseThrow(()->new ArticleException(String.format("[ARTICLE: %s] Article not found",id)));
		if(null != article.getStock() && article.getStock().getQuantity()>0 && (article.getStock().getQuantity() - quantity)>0) {
			StockEntity stockEntity = article.getStock();
			stockEntity.setQuantity(stockEntity.getQuantity()+quantity);
			stockEntity = stockRepository.save(stockEntity);
			if(stockEntity.getId()>0)
			{
				return stockMapper.toDto(stockEntity);
			}else
			{
				throw new StockException(String.format("[ARTICLE: %s QUANTITY: %s] Stock not updated",id,quantity));
			}
		}
		throw new StockException(String.format("[ARTICLE: %s QUANTITY: %s] Stock not updated",id,quantity));
	}
}
