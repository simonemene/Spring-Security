package com.store.security.store_security.service;

import com.store.security.store_security.StoreSecurityApplicationTests;
import com.store.security.store_security.dto.ArticleDto;
import com.store.security.store_security.entity.*;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.mapper.ArticleMapper;
import com.store.security.store_security.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceIntegrationTest extends StoreSecurityApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private ArticleMapper articleMapper;

	@Test
	public void addOrder() throws OrderException {
		//given
		AuthoritiesEntity authoritiesEntity = AuthoritiesEntity.builder().authority("ROLE_USER").build();
		UserEntity user = UserEntity.builder().age(21).username("test").password("test").authoritiesList(
				List.of(authoritiesEntity)).tmstInsert(LocalDateTime.of(2021, 1, 1, 1, 1)).build();
		userRepository.save(user);
		ArticleEntity articleEntity = ArticleEntity.builder().name("test").description("test").price(new BigDecimal(10)).tmstInsert(
				LocalDateTime.of(2021, 1, 1, 1, 1)).build();
		ArticleEntity articleEntity1 = ArticleEntity.builder().name("test1").description("test1").price(new BigDecimal(10)).tmstInsert(
				LocalDateTime.of(2021, 1, 1, 1, 1)).build();
		articleRepository.save(articleEntity);
		StockEntity stockEntity = stockRepository.save(StockEntity.builder().article(articleEntity).quantity(2).build());
		stockRepository.save(stockEntity);

		Iterable<ArticleEntity> articleEntities = articleRepository.findAll();
		List<ArticleDto> articleDtos = new ArrayList<>();
		for(ArticleEntity article : articleEntities) {
			articleDtos.add(articleMapper.toDto(article));
		}
		//when
		boolean result = orderService.addOrder(articleDtos, user.getUsername());
		//then
		Assertions.assertThat(result).isTrue();

		Iterable<OrderLineEntity> orderLineEntities = orderLineRepository.findAll();

		List<OrderLineEntity> orderLine = new ArrayList<>();

		for(OrderLineEntity orderLineEntity : orderLineEntities) {
			orderLine.add(orderLineEntity);
		}

		Assertions.assertThat(orderLine).hasSize(1);

		Optional<OrderLineEntity> orderLineEntity = orderLineRepository.findByArticle_IdAndOrder_User_Id(articleEntity.getId(), user.getId());
		Optional<OrderEntity> orderEntity = orderRepository.findByUser(user);

		Assertions.assertThat(orderLineEntity.isPresent()).isTrue();
		Assertions.assertThat(orderEntity.isPresent()).isTrue();
		Assertions.assertThat(orderLineEntity.get().getArticle()).usingRecursiveComparison().ignoringFields("price").isEqualTo(articleEntity);
		Assertions.assertThat(orderLineEntity.get().getOrder()).usingRecursiveComparison().isEqualTo(orderEntity.get());
	}

	@Test
	public void getOrders()
	{
		//given

		//when

		//then
	}
}
