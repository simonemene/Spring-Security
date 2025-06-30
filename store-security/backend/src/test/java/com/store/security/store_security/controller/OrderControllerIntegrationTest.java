package com.store.security.store_security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.controladvice.GenericExceptionHandler;
import com.store.security.store_security.dto.AllArticleOrderDto;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.dto.ArticlesOrderDto;
import com.store.security.store_security.entity.*;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.assertj.core.api.Assertions;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private TrackRepository trackRepository;


	@Test
	@WithMockUser(username = "utente@gmail.com", roles = "USER")
	public void createOrder() throws Exception {
		//given

		AuthoritiesEntity authorities = AuthoritiesEntity.builder()
				.authority("ROLE_USER").build();
		authoritiesRepository.save(authorities);
		UserEntity user = UserEntity.builder()
				.tmstInsert(LocalDateTime.now())
				.authoritiesList(Set.of(authorities))
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
				.andExpect(MockMvcResultMatchers.status().isCreated());
		//then
		Iterable<OrderEntity> orders = orderRepository.findAll();
		OrderEntity order = orders.iterator().next();
		Assertions.assertThat(order.getUser().getUsername()).isEqualTo(articles.getUsername());
		List<OrderLineEntity> orderLines = orderLineRepository.findByOrder_Id(order.getId());
		Assertions.assertThat(orderLines.size()).isEqualTo(1);
		Assertions.assertThat(orderLines.getFirst().getArticle().getName()).isEqualTo(article.getName());
		Assertions.assertThat(orderLines.getFirst().getOrder().getUser().getUsername()).isEqualTo(articles.getUsername());
		Iterable<TrackEntity> trackEntity = trackRepository.findAll();
		Assertions.assertThat(trackEntity.iterator().next().getOrder().getId()).isEqualTo(order.getId());
		List<TrackEntity> listTrack = StreamSupport.stream(trackEntity.spliterator(),false).toList();
		Assertions.assertThat(listTrack.size()).isEqualTo(1);


	}
}
