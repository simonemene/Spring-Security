package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articoleRepository;

    private final StockRepository stockRepository;

    @Transactional
    @Override
    public boolean saveArticle(ArticleEntity article) {
        try {
            ArticleEntity savedArticle = articoleRepository.save(article);
            StockEntity savedStock = stockRepository.save(
                    StockEntity.builder().article(savedArticle).quantity(1).build()
            );
            return savedArticle.getId() > 0 && savedStock.getId() > 0;
        } catch (Exception e) {
            log.error("Error saving article", e);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteArticle(long id) {
        try {
            articoleRepository.deleteById(Integer.parseInt(String.valueOf(id)));
        } catch (Exception e) {
            log.error("Error delete article", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean saveArticleQuantity(long id, int quantity) {
        try
        {
            Optional<ArticleEntity> article = articoleRepository.findById(Integer.parseInt(String.valueOf(id)));
            if(article.isPresent())
            {
                Optional<StockEntity> stockEntity = stockRepository.findByArticle(article.get());
                if(stockEntity.isPresent())
                {
                    stockEntity.get().setQuantity(stockEntity.get().getQuantity() + quantity);
                    StockEntity savedStock = stockRepository.save(stockEntity.get());
                    return savedStock.getId() > 0;
                }
            }
            return false;

        }catch(Exception e)
        {
            log.error(String.format("Error saving article: %s quantity: %s",id,quantity), e);
            return false;
        }
    }

}
