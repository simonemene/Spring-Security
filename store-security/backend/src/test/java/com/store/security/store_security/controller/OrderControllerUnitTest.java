package com.store.security.store_security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.controladvice.GenericExceptionHandler;
import com.store.security.store_security.dto.AllArticleOrderDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.repository.UserRepository;
import com.store.security.store_security.service.OrderService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebMvcTest(OrderController.class)
@Import(GenericExceptionHandler.class)
public class OrderControllerUnitTest {

	@MockitoBean
	private OrderService orderService;

	@Autowired
	private MockMvc mockMvc;



	@Test
	@WithMockUser(username = "utente@gmail.com", roles = "USER")
	public void createOrderFailed() throws Exception {
		//given
		ObjectMapper objectMapper = new ObjectMapper();
		ArticleDto articleDto = ArticleDto.builder()
				.name("car").description("test").price(BigDecimal.TEN).tmstInsert(LocalDateTime.now()).id(1L).build();
		AllArticleOrderDto allArticleOrderDto = AllArticleOrderDto.builder().quantity(1).articleDto(articleDto).build();
		List<AllArticleOrderDto> allArticleOrderDtos = List.of(allArticleOrderDto);
		ArticlesOrderDto articles = ArticlesOrderDto.builder().idOrder(1L).articles(allArticleOrderDtos).username("admin@gmail.com").build();

		Mockito.when(orderService.orderArticles(Mockito.any(ArticlesOrderDto.class)))
				.thenThrow(new OrderException("order not found"));
		String json = objectMapper.writeValueAsString(articles);

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
						.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("order not found"));
	}

	@Test
	public void getAllOrder()
	{
		//given

		//when

		//then
	}
}
