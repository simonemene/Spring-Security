package com.store.security.store_security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.service.IArticleService;
import com.store.security.store_security.service.IStockService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
public class ArticleControllerIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ArticleRepository articleRepository;

	private ArticleController controller;


	@Test
	@WithMockUser(username = "admin@gmail.com",roles={"ADMIN"})
	public void addArticle() throws Exception {
		//given
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build();
		String json = objectMapper.writeValueAsString(articleEntity);
		//when
		mockMvc.perform(post("/api/article/addArticle").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Article added"));
		//then
		ArticleEntity result = null;
		Iterable<ArticleEntity> article = articleRepository.findAll();
        for (ArticleEntity entity : article) {
            result = entity;
            if (result.getName().equals("test")) {
                break;
            }
        }
		Assertions.assertThat(result.getName()).isEqualTo("test");
	}


}
