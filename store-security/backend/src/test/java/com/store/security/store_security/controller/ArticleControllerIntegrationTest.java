package com.store.security.store_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.entity.ArticleEntity;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
public class ArticleControllerIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private IArticleService articleService;

	@Mock
	private IStockService stockService;

	private ArticleController controller;


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		controller = new ArticleController(articleService, stockService);
	}


	@Test
	@DisplayName("add article")
	@WithMockUser(username="admin@gmail.com",roles={"ADMIN"})
	public void addArticle()
	{
        //given
		Mockito.when(articleService.saveArticle(Mockito.any())).thenReturn(true);
		//when
		ResponseEntity<String> response = controller.addArticle(ArticleEntity.builder().id(1).name("test").description("test").price(new BigDecimal(1))
				.tmstInsert(LocalDateTime.now()).tmstInsert(LocalDateTime.now()).build());
		//then
		Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
		Assertions.assertThat(response.getBody()).isEqualTo("Article added");
	}

	@Test
	@DisplayName("add article")
	@WithMockUser(username="user@gmail.com",roles={"USER"})
	public void addArticleNoAuth() throws Exception {
		//given
		Mockito.when(articleService.saveArticle(Mockito.any())).thenReturn(true);
		//when
		//then
		mockMvc.perform(post("/api/article/addArticle").contentType("application/json")
				.content(objectMapper.writeValueAsString(ArticleEntity.builder().description("descrizione").tmstInsert(LocalDateTime.now()).price(new BigDecimal(1))
						.name("articolo").build())))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
