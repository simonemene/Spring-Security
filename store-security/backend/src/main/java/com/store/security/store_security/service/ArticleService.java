package com.store.security.store_security.service;

import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articoleRepository;


    @Override
    public boolean saveArticle(ArticleEntity articleEntity) {
        ArticleEntity article = articoleRepository.save(articleEntity);
        return article.getId() > 0;
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
