package com.store.security.store_security.repository;

import com.store.security.store_security.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticoleRepository extends CrudRepository<ArticleEntity, Integer> {
}
