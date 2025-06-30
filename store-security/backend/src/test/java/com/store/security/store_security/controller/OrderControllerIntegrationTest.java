package com.store.security.store_security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.controladvice.GenericExceptionHandler;
import com.store.security.store_security.dto.AllArticleOrderDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.entity.ArticleEntity;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.ArticleRepository;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AutoConfigureMockMvc
@Import(GenericExceptionHandler.class)
public class OrderControllerIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private  OrderController orderController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;


	@Test
	@WithMockUser(username = "utente@gmail.com", roles = "USER")
	public void createOrder() throws Exception {
		//given

		AuthoritiesEntity authorities = AuthoritiesEntity.builder()
				.authority("ROLE_USER").build();
		authoritiesRepository.save(authorities);
		UserEntity user = UserEntity.builder()
				.tmstInsert(LocalDateTime.now())
				.authoritiesList(Set.of(AuthoritiesEntity.builder()
						.authority("ROLE_USER").build()))
				.username("utente@gmail.com").age(21).build();


		userRepository.save(user);


		ArticleDto articleDto = ArticleDto.builder()
				.name("car").description("test").price(BigDecimal.TEN).tmstInsert(
						LocalDateTime.now()).id(1L).build();
		ArticleEntity article = ArticleEntity.builder().name(articleDto.getName())
				.description(articleDto.getDescription()).price(articleDto.getPrice())
				.tmstInsert(articleDto.getTmstInsert()).build();
		articleRepository.save(article);
		AllArticleOrderDto allArticleOrderDto = AllArticleOrderDto.builder().quantity(1).articleDto(articleDto).build();
		List<AllArticleOrderDto> allArticleOrderDtos = List.of(allArticleOrderDto);
		ArticlesOrderDto articles = ArticlesOrderDto.builder().idOrder(1L).articles(allArticleOrderDtos).username("utente@gmail.com").build();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(articles);
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders").contentType("application/json").content(json)
						.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		//then
	}
}
