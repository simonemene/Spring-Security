package com.store.security.store_security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ArticlesOrderDto {

	private List<AllArticleOrderDto> articles;

	private Long idOrder;

	private String username;

	public void addArticle(AllArticleOrderDto article) {
		if(null == articles) {
			articles = new ArrayList<>();
			articles.add(article);
		}else
		{
			articles.add(article);
		}
	}
}
