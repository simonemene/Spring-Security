package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articoleRepository;

    private final StockRepository stockRepository;


    @Override
    public boolean saveArticle(ArticleEntity articleEntity) {
        ArticleEntity article = articoleRepository.save(articleEntity);
        StockEntity stockEntity = StockEntity.builder().article(article).quantity(1).build();
        StockEntity resultStock = stockRepository.save(stockEntity);
        return article.getId() > 0 && resultStock.getId() > 0;
    }

    @Override
    public boolean deleteArticle(long id) {
        try {
            articoleRepository.deleteById(Integer.parseInt(String.valueOf(id)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }



}
